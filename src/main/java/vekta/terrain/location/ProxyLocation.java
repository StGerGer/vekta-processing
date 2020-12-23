package vekta.terrain.location;

import vekta.menu.Menu;
import vekta.menu.handle.MenuHandle;
import vekta.object.planet.TerrestrialPlanet;
import vekta.player.Player;
import vekta.spawner.location.ProxyLocationSpawner;

import java.io.Serializable;
import java.util.Set;

public class ProxyLocation<T extends Serializable> extends Location {

	private final Class<? extends ProxyLocationSpawner<T>> cls;

	private T data;

	private transient ProxyLocationSpawner<T> proxy;

	public ProxyLocation(TerrestrialPlanet planet, Class<? extends ProxyLocationSpawner<T>> cls, T data) {
		super(planet);

		this.cls = cls;

		setData(data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	protected ProxyLocationSpawner<T> getProxy() {
		if(proxy == null) {
			proxy = ProxyLocationSpawner.findProxy(cls);
			if(proxy == null) {
				throw new RuntimeException("Spawner not found for " + cls.getSimpleName());
			}
		}
		return proxy;
	}

	@Override
	public String getName() {
		return getProxy().getName(this);
	}

	@Override
	public String getOverview() {
		return getProxy().getOverview(this);
	}

	@Override
	public int getColor() {
		int color = getProxy().getColor(this);
		return color != 0 ? color : super.getColor();
	}

	@Override
	public boolean isEnabled() {
		return getProxy().isEnabled(this);
	}

	@Override
	public boolean isVisitable(Player player) {
		return getProxy().isVisitable(this);
	}

	@Override
	protected void onSurveyTags(Set<String> tags) {
		getProxy().onSurveyTags(this, tags);
	}

	@Override
	protected MenuHandle createMenuHandle() {
		MenuHandle handle = getProxy().overrideMenuHandle(this);
		return handle != null ? handle : super.createMenuHandle();
	}

	@Override
	public void onVisitMenu(Menu menu) {
		getProxy().onVisitMenu(this, menu);
	}

	/// Shorthand methods

	public float mass() {
		return getPlanet().getMass();
	}

	public float tempC() {
		return getPlanet().getTemperatureCelsius();
	}

	public boolean tempC(float min, float max) {
		return between(tempC(), min, max);
	}

	public float atm() {
		return getPlanet().getAtmosphereDensity();
	}

	public boolean atm(float min, float max) {
		return between(atm(), min, max);
	}

	public boolean between(float value, float min, float max) {
		return value >= min && value <= max;
	}
}
