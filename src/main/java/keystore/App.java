/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package keystore;

import com.google.gson.Gson;
import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusRenameFilter;
import keystore.exceptions.HttpException;
import keystore.exceptions.ResourceNotFoundException;
import keystore.handler.WatchHandler;
import keystore.models.ErrorResponse;
import keystore.models.KeyValue;
import keystore.models.StatusResponse;
import keystore.services.KeyValueStoreService;
import keystore.services.KeyValueStoreServiceImpl;
import spark.Request;
import spark.Response;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static spark.Spark.*;


public class App {
    public static void main(String[] args) {
        final KeyValueStoreService keyValueStoreService = new KeyValueStoreServiceImpl();
        final PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        prometheusRegistry.config().meterFilter(new PrometheusRenameFilter());
        prometheusRegistry.config().commonTags("application", "KeyValue");
        new ProcessMemoryMetrics().bindTo(prometheusRegistry);
        new ProcessThreadMetrics().bindTo(prometheusRegistry);
        AtomicInteger watchGauge = prometheusRegistry.gauge("KeyValueStore.watches", new AtomicInteger(0));
        Counter addRequestCounter = prometheusRegistry.counter("http.request",
                "uri", "/keyvalue",
                "operation", "add");
        Counter getAllRequestCounter = prometheusRegistry.counter("http.request",
                "uri", "/keyvalue",
                "operation", "getAll");
        Counter getRequestCounter = prometheusRegistry.counter("http.request",
                "uri", "/keyvalue/:key",
                "operation", "get");
        Counter getSuccessCounter = prometheusRegistry.counter("http.request.success",
                "uri", "/keyvalue/:key",
                "operation", "get");
        Counter getFailureCounter = prometheusRegistry.counter("http.request.failure",
                "uri", "/keyvalue/:key",
                "operation", "get");
        Counter deleteRequestCounter = prometheusRegistry.counter("http.request",
                "uri", "/keyvalue/:key",
                "operation", "delete");
        Counter deleteSuccessCounter = prometheusRegistry.counter("http.request.success",
                "uri", "/keyvalue/:key",
                "operation", "delete");
        Counter deleteFailureCounter = prometheusRegistry.counter("http.request.failure",
                "uri", "/keyvalue/:key",
                "operation", "delete");

        final WatchHandler watchHandler = new WatchHandler(keyValueStoreService, watchGauge);
        webSocket("/watch", watchHandler);

        post("/keyvalue", (request, response) -> {
            addRequestCounter.increment();
            response.type("application/json");

            KeyValue Values = new Gson().fromJson(request.body(), KeyValue.class);
            keyValueStoreService.add(Values);

            return new Gson().toJson(StatusResponse.SUCCESS);
        });

        get("/keyvalue", (request, response) -> {
            getAllRequestCounter.increment();
            response.type("application/json");

            return new Gson().toJson(keyValueStoreService.getAll());
        });

        get("/keyvalue/:key", (request, response) -> {
            getRequestCounter.increment();
            response.type("application/json");
            Optional<KeyValue> maybeKeyValue = keyValueStoreService.get(request.params("key"));

            if (maybeKeyValue.isPresent()) {
                getSuccessCounter.increment();
            } else {
                getFailureCounter.increment();
            }
            return new Gson().toJson(maybeKeyValue.orElseThrow(ResourceNotFoundException::new));
        });

        delete("/keyvalue/:key", (request, response) -> {
            deleteRequestCounter.increment();
            response.type("application/json");

            if (keyValueStoreService.delete(request.params(":key"))) {
                deleteSuccessCounter.increment();
                return new Gson().toJson(StatusResponse.SUCCESS);
            }
            deleteFailureCounter.increment();
            throw new ResourceNotFoundException();
        });

        get("/metrics", (request, response) -> prometheusRegistry.scrape());

        notFound(App::notFoundHandler);
        //exception(HttpException.class, App::exceptionHandler);
        exception(Exception.class, App::exceptionHandler);
    }

    private static String notFoundHandler(Request request, Response response) {
        response.status(404);
        return new Gson().toJson(new ErrorResponse("Not found"));
    }

    private static void exceptionHandler(Exception t, Request request, Response response) {
        int status = 500;
        String message = "Internal server error";

        if (t instanceof HttpException) {
            HttpException exception = (HttpException) t;
            status = exception.getStatusCode();
            message = exception.getMessage();
        }

        response.status(status);
        response.body(new Gson().toJson(new ErrorResponse(message)));
    }

}

