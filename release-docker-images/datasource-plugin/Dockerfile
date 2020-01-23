FROM grafana/grafana

ADD ./latest-grafana-kafka-plugin-*.tar.gz /var/lib/grafana/plugins/grafana-kafka-datasource-plugin/

EXPOSE 3000

CMD ["/run.sh"]