# lorca

### REST service
A jaxrs java war. This jaxrs rest service implementa

 * / endopint, it is necesary to test datasource from grafana gui
 * /search, return the array of vars
 * /query, return the datapoints to plot
 * /... Not implemente yet

### Grafana datasource plugin

The java script project that implementa the datasource connector. To deploy into existing grafana
 * first build binary with yarn build and yarn install
 * then copy dist and other resources to grafana plugin dir
 * restart grafana
The rest base end point is http://server/grafana-kafka-rest/api/v1

## Development

Link the plugin with grafana

```bash
sudo ln -s /home/gus/git/lorca/kafka-datasource-plugin /var/lib/grafana/plugins/kafka-datasource-plugin
```

Restart grafana
```bash
sudo systemctl status grafana-server
```
