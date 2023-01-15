import dk.mhm.physics.Mechanics;
import org.djunits.unit.*;
import org.djunits.value.vdouble.scalar.*;
import org.junit.jupiter.api.Test;

public class DJUnitsExampleTest {

    /**
     * Example from the DJUnits manual at https://djunits.org/manual/.
     */
    @Test
    public void testDJUnits() {
        Speed speed1 = new Speed(30, SpeedUnit.MILE_PER_HOUR);
        System.out.println("speed1:     " + speed1);
        Speed speed2 = new Speed(10, SpeedUnit.METER_PER_SECOND);
        System.out.println("speed2:     " + speed2);
        Speed diff = speed1.minus(speed2);

        // Default display unit will be SI unit for speed:
        System.out.println("difference: " + diff);

        // Change default display unit; internal SI value is unaltered:
        diff.setDisplayUnit(SpeedUnit.MILE_PER_HOUR);
        System.out.println("difference: " + diff);

        // Works, but not mistake-safe:
        System.out.println("difference: " + diff.getInUnit(SpeedUnit.KNOT) + " kt");

        // Safer:
        System.out.println("difference: " + diff.toString(SpeedUnit.KNOT));

        // Programmer must be really sure that SI-unit is m/s:
        System.out.println("difference: " + diff.si + " m/s (si)");

        // Same as previous:
        System.out.println("difference: " + diff.getSI() + " m/s (si)");

        // Safer:
        System.out.println("difference: " + diff.toString(SpeedUnit.SI) + " (si)");
        System.out.println("difference: " + diff.toString(SpeedUnit.KM_PER_HOUR));

        Speed speedDelta = speed1.minus(speed2);
        System.out.println("Speed delta: " + speedDelta);
    }

    /**
     * Kinetic energy calculation. First based on the base SI units. After that with converted units,
     * but still yielding the same result.
     */
    @Test
    public void kineticEnergyFromDifferentBases() {

        // calculating the kinetic energy of an object - E = 1/2 * M * v^2
        Mass mass = new Mass(1000, MassUnit.KILOGRAM);
        Speed speed = new Speed(50, SpeedUnit.METER_PER_SECOND);
        Energy energy = Mechanics.kineticEnergy(mass, speed);
        System.out.println("Energy = "+ energy);

        // even when we convert the mass to pounds and the speed to miles/hr, the total amount of kinetic energy is the same
        Mass convertedMass = new Mass(mass.getInUnit(MassUnit.POUND), MassUnit.POUND);
        System.out.println("convertedMass = "+ convertedMass);
        Speed convertedSpeed = new Speed(speed.getInUnit(SpeedUnit.MILE_PER_HOUR), SpeedUnit.MILE_PER_HOUR);
        System.out.println("convertedSpeed = "+ convertedSpeed);

        Energy newEnergy = Mechanics.kineticEnergy(convertedMass, convertedSpeed);
        System.out.println("newEnergy = "+ newEnergy);
    }

    @Test
    public void testHeightEnergy() {
        Mass mass = new Mass(10, MassUnit.KILOGRAM);
        Speed speed = new Speed(13.9, SpeedUnit.METER_PER_SECOND);

        Energy kineticEnergy = Mechanics.kineticEnergy(mass, speed);
        Length height = Mechanics.heightFromEnergy(kineticEnergy, mass);

        System.out.println("Height reached: " + height);
    }

}
