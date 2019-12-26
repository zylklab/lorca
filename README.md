# lorca

### REST service

### Grafana datasource plugin

## Development

Link the plugin with grafana

```bash
sudo ln -s /home/gus/git/lorca/kafka-datasource-plugin /var/lib/grafana/plugins/kafka-datasource-plugin
```

Restart grafana
```bash
sudo systemctl status grafana-server
```
