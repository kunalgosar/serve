package org.pytorch.serve.metrics.format.prometheous;

import io.prometheus.client.Gauge;
import java.util.List;
import org.pytorch.serve.metrics.MetricBuilder;

public class PrometheusGauge extends PrometheusMetric {
    private final Gauge gauge;

    public PrometheusGauge(
            MetricBuilder.MetricContext context,
            MetricBuilder.MetricType type,
            String name,
            String unit,
            List<String> dimensionNames) {
        super(context, type, name, unit, dimensionNames);
        this.gauge =
                Gauge.build()
                        .name(this.name)
                        .labelNames(
                                this.dimensionNames.toArray(new String[this.dimensionNames.size()]))
                        .help("Torchserve prometheus gauge metric with unit: " + this.unit)
                        .register();
    }

    @Override
    public void addOrUpdate(List<String> dimensionValues, double value) {
        this.gauge.labels(dimensionValues.toArray(new String[dimensionValues.size()])).set(value);
    }
}
