package vekta.detail;

import java.io.Serializable;

public interface Detailed extends Serializable {
	Details getDetails();

	default boolean hasDetail(Class<? extends Detail> cls) {
		return getDetails().has(cls);
	}

	default <T extends Detail> T getDetail(Class<T> cls) {
		return getDetails().get(cls);
	}
}
