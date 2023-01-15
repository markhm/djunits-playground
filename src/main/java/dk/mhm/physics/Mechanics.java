package dk.mhm.physics;

import org.djunits.unit.EnergyUnit;
import org.djunits.unit.LengthUnit;
import org.djunits.value.vdouble.scalar.Energy;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.Mass;
import org.djunits.value.vdouble.scalar.Speed;

public class Mechanics {

    public static Energy kineticEnergy(Mass mass, Speed speed) {
        return new Energy((0.5 * mass.doubleValue() * (speed.doubleValue() * speed.doubleValue())), EnergyUnit.JOULE);
    }

    public static Length heightFromEnergy(Energy energy, Mass mass) {
        return new Length(energy.doubleValue() / mass.doubleValue(), LengthUnit.METER);
    }

    public static Energy heightEnergy(Mass mass, Length height) {
        return new Energy (mass.doubleValue() * height.doubleValue(), EnergyUnit.JOULE);
    }
}
