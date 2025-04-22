package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;

import java.math.BigDecimal;

public class Bird extends Pet implements PetImpl {

    private int numberOfLegs;
    private Breed breed;

    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed) {
        this(animalType, skinType, gender, breed, new BigDecimal(0));
    }

    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost) {
        this(animalType, skinType, gender, breed, cost, 0);
    }

    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost, int petStoreId) {
        super(PetType.BIRD, cost, gender, petStoreId);
        super.animalType = animalType;
        super.skinType = skinType;
        this.breed = breed;
        this.numberOfLegs = 2;
    }

    public String birdHypoallergenic() {
        return super.petHypoallergenic(this.skinType).replaceAll("pet", "bird");
    }

    public String speak() {
        switch (animalType) {
            case DOMESTIC: return "The bird goes chirp! chirp!";
            case WILD: return "The bird goes squawk! squawk!";
            default: return "The bird goes " + super.getPetType().speak + "! " + super.getPetType().speak + "!";
        }
    }


    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    @Override
    public Breed getBreed() {
        return breed;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "The bird is " + animalType + "!\n" +
                "The bird's breed is " + breed + "!\n" +
                birdHypoallergenic() + "!\n" +
                speak() + "\n" +
                "Birds have " + numberOfLegs + " legs!";
    }
}

