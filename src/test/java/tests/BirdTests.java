package tests;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Bird;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BirdTests {

    private static Bird testBird;

    @BeforeAll
    public static void createBird() {
        testBird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.BLUE_JAY);
    }

    @Test
    @DisplayName("Bird Animal Type Test")
    public void birdAnimalTypeTest() {
        assertEquals(AnimalType.DOMESTIC, testBird.getAnimalType());
    }

    @Test
    @DisplayName("Bird Speak Test")
    public void birdSpeakTest() {
        assertEquals("The bird goes chirp! chirp!", testBird.speak());
    }

    @Test
    @DisplayName("Bird Hypoallergenic Test")
    public void birdHypoallergenicTest() {
        assertEquals("The bird is not hyperallergetic!", testBird.birdHypoallergenic());
    }

    @Test
    @DisplayName("Bird Leg Count Test")
    public void birdLegCountTest() {
        assertEquals(2, testBird.getNumberOfLegs());
    }

    @Test
    @DisplayName("Bird Breed Test")
    public void birdBreedTest() {
        assertEquals(Breed.BLUE_JAY, testBird.getBreed());
    }
}
