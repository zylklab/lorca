package net.zylklab.grafana.kafka.util;

import java.util.function.Predicate;

public class FilterWithGap implements Predicate<Object[]> {

	private Long gap;
	private Long date;
	public FilterWithGap(Long gap) {
		this.gap = gap;
	}
	
	@Override
	public boolean test(Object[] t) {
		if(date != null && (((long)t[1] - gap) < date)) {
			return false;
		} else {
			this.date = (long)t[1];
			return true;
		}
	}

}
