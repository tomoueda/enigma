package enigma;
import static org.junit.Assert.*;
import org.junit.Test;

import static enigma.Rotor.*;
import static enigma.Machine.*;
import static enigma.Main.*;

/** Unit Testing for the enigma project.
* @author Tomo Ueda
*/
public class MainTest {

	@Test
	public void testRotor() {
		assertEquals("Testing toLetter", 'A', toLetter(0));
		assertEquals("Testing toLetter", 'B', toLetter(1));
		assertEquals("Testing toLetter", 'Z', toLetter(25));

		assertEquals("Testing toIndex", 0, toIndex('A'));
		assertEquals("Testing toIndex", 25, toIndex('Z'));

		assertEquals("Testing arithmetic", 0, -3%3);
		Rotor testRotor = new Rotor(0, 0);
		assertEquals("Testing the Rotors", 4,
			testRotor.convertForward(0));
		testRotor.setPosition(1);
		assertEquals("Testing forward convert", 9,
			testRotor.convertForward(0));
		testRotor.setPosition(0);
		assertEquals("Testing backward convert", 0,
			testRotor.convertBackward(4));
		testRotor.setPosition(1);
		assertEquals("Testing backward convert", 0,
			testRotor.convertBackward(9));
		testRotor.setPosition(16);
		assertTrue(testRotor.atNotch());
		Rotor rotorMoreThan5 = new Rotor(25, 5);
		assertTrue(rotorMoreThan5.atNotch());
		rotorMoreThan5.advance();
		assertEquals(rotorMoreThan5.getPosition(), 0);
		rotorMoreThan5.setPosition(12);
		assertTrue(rotorMoreThan5.atNotch());
	}

	@Test
	public void testMachine() {
		Machine M = new Machine();
		M.setRotors(8, 1, 2, 3);
		M.setPositions("AXEL");
		assertEquals(0, M.getReflectPos());
		assertEquals(23, M.getLRotorPos());
		assertEquals(4, M.getMRotorPos());
		assertEquals(11, M.getRRotorPos());
		M.setRotors(8, 2, 3, 0);
		M.setPositions("AXLE");
		String encoded = M.convert("FROM h");
		assertEquals("HYIHL", encoded);
	}

	@Test
	public void testMain() {
		assertTrue(isConfigurationLine("* B III IV I AXLE"));
		assertTrue(!isConfigurationLine("alpha"));
		assertTrue(!isConfigurationLine("* B III IV I AXELER"));
		assertTrue(!isConfigurationLine("* D III IV I AXEL"));
		assertTrue(isConfigurationLine("* C II I VIII AAAA"));
		assertTrue(!isConfigurationLine("* B III II II AAAA"));
		assertTrue(!isConfigurationLine("* C VI VI I ABCD"));
		Machine M = new Machine();
		String config = "* B II III IV AXEL";
		configure(M, config);
		assertEquals(0, M.getReflectPos());
		assertEquals(23, M.getLRotorPos());
		assertEquals(4, M.getMRotorPos());
		assertEquals(11, M.getRRotorPos());
	}
}