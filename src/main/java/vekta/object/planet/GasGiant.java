package vekta.object.planet;

import processing.core.PShape;
import processing.core.PVector;
import vekta.world.RenderLevel;

import static processing.core.PApplet.sq;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.ELLIPSE;
import static vekta.Vekta.v;

public class GasGiant extends TerrestrialPlanet {
	private final float ringAngle;
	private final float ringRatio;
	private final float[] ringDistances;
	private final int[] ringColors;
	private final float maxRadius;

	private transient PShape[] rings;

	public GasGiant(String name, float mass, float density, PVector position, PVector velocity, int color) {
		super(name, mass, density, position, velocity, color);
		ringAngle = v.random(360);
		ringRatio = v.random(.1F, 1);
		ringDistances = new float[(int)v.random(2, 5)];
		ringColors = new int[ringDistances.length];
		float d = v.random(1.5F, 3);
		for(int i = 0; i < ringDistances.length; i++) {
			ringDistances[i] = d *= v.random(1.01F, 1.5F);
			ringColors[i] = v.lerpColor(20, 70, v.random(1));
		}
		maxRadius = d;
	}

	@Override
	protected float chooseAtmosphereDensity() {
		// TODO: figure out how this'll work
		return 1;
	}

	@Override
	protected float chooseRotationHours() {
		return super.chooseRotationHours() * .5f;
	}

	public boolean isInsideRings(PVector pos) {
		float distSq = getPosition().sub(pos).magSq();
		return distSq <= sq(getRadius() * ringDistances[ringDistances.length - 1]);
	}

	@Override
	public float getOnScreenRadius(float r) {
		return r * maxRadius;
	}

	@Override
	public RenderLevel getRenderLevel() {
		return RenderLevel.INTERSTELLAR;
	}

	@Override
	public void drawNearby(float r) {
		super.drawNearby(r);

		// Initialize in render loop for consistent rendering parameters
		if(rings == null) {
			rings = new PShape[ringDistances.length];
			v.noFill();
			v.shapeMode(CENTER);
			for(int i = 0; i < ringDistances.length; i++) {
				float rd = ringDistances[i];
				v.stroke(ringColors[i]);
				PShape ring = v.createShape(ELLIPSE, rd, rd * ringRatio, rd, rd * ringRatio);
				rings[i] = ring;
			}
		}

		v.scale(r);
		v.rotate(ringAngle);
		for(PShape ring : rings) {
			ring.setStrokeWeight(1 / r);
			v.shape(ring);
		}
	}
}
