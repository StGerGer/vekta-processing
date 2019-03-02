package vekta.object;

import processing.core.PVector;
import vekta.terrain.MoltenTerrain;
import vekta.terrain.Terrain;

import static vekta.Vekta.*;

/**
 * Model for a planet.
 */
public abstract class Planet extends SpaceObject {
	private static final float MIN_SPLIT_RADIUS = 6;
	private static final float SPLIT_OFFSET_SCALE = .25F;
	private static final float SPLIT_VELOCITY_SCALE = 1;
	private static final float SPLIT_MASS_ABSORB = .5F; // Below 1.0 to prevent supermassive planets taking over the map

	private final String name;
	private float mass;
	private final float density;

	private float radiusCache;

	public Planet(float mass, float density, PVector position, PVector velocity, int color) {
		super(position, velocity, color);
		this.name = generatePlanetName();
		this.mass = mass;
		this.density = density;

		updateRadius();
	}

	@Override
	public void draw() {
		v.stroke(getColor());
		v.fill(0);
		v.ellipseMode(RADIUS);
		float radius = getRadius();
		v.ellipse(position.x, position.y, radius, radius);
	}

	@Override
	public boolean collidesWith(SpaceObject s) {
		// TODO: check getParent() once added to SpaceObject
		return getColor() != s.getColor() && super.collidesWith(s);
	}

	@Override
	public void onCollide(SpaceObject s) {
		if(getMass() * 2 >= s.getMass()) {
			s.destroyBecause(this);
		}
	}
	
	public boolean doesSplitOnDestroy() {
		return getRadius() >= MIN_SPLIT_RADIUS;
	}

	@Override
	public void onDestroy(SpaceObject s) {
		//println("Planet destroyed with radius: " + getRadius());

		// If sufficiently large, split planet in half
		if(doesSplitOnDestroy()) {
			float newMass = getMass() / 2;

			// Use mass-weighted collision velocity for base debris velocity
			float xWeight = getVelocity().x * getMass() + s.getVelocity().x * s.getMass();
			float yWeight = getVelocity().y * getMass() + s.getVelocity().y * s.getMass();
			float massSum = getMass() + s.getMass();
			PVector newVelocity = new PVector(xWeight / massSum, yWeight / massSum);

			PVector base = getPosition().copy().sub(s.getPosition()).normalize().rotate(PI / 2);
			PVector offset = base.copy().mult(getRadius() * SPLIT_OFFSET_SCALE);
			PVector splitVelocity = base.copy().mult(SPLIT_VELOCITY_SCALE);
			Terrain terrain = new MoltenTerrain();
			Planet a = new TerrestrialPlanet(newMass, getDensity(), terrain, getPosition().copy().add(offset), newVelocity.copy().add(splitVelocity), getColor());
			Planet b = new TerrestrialPlanet(newMass, getDensity(), terrain, getPosition().copy().sub(offset), newVelocity.copy().sub(splitVelocity), getColor());
			// TODO: fix weird behaviour when landing on newly created planet
			if(!s.collidesWith(a)) {
				mass -= a.mass;
				addObject(a);
			}
			if(!s.collidesWith(b)) {
				mass -= b.mass;
				addObject(b);
			}
		}

		// If this is a planetary collision, add some additional mass to the other planet
		if(mass > 0 && s instanceof Planet) {
			Planet p = (Planet)s;
			p.setMass(p.getMass() + mass * SPLIT_MASS_ABSORB);
		}
	}

	@Override
	public float getMass() {
		return mass;
	}

	@Override
	public String getName() {
		return name;
	}

	void setMass(float mass) {
		this.mass = mass;
		updateRadius();
	}

	@Override
	public float getRadius() {
		return radiusCache;
	}

	private void updateRadius() {
		radiusCache = pow(getMass() / getDensity(), (float)1 / 3) / SCALE;
	}

	public float getDensity() {
		return density;
	}

	public abstract boolean isHabitable();
}
