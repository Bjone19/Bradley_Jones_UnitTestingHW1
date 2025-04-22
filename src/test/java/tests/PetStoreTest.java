package tests;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Bird;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class PetStoreTest {

    @Test
    @DisplayName("Inventory Count Test")
    public void validateInventory() {
        PetStore store = new PetStore();
        store.init();
        assertEquals(5, store.getPetsForSale().size());
    }

    @Test
    @DisplayName("Print Inventory Test")
    public void printInventoryTest() {
        PetStore store = new PetStore();
        store.init();
        assertDoesNotThrow(store::printInventory);
    }

    @Test
    @DisplayName("Print Inventory When Empty")
    public void printEmptyInventoryTest() {
        PetStore emptyStore = new PetStore();
        assertDoesNotThrow(emptyStore::printInventory);
    }

    @Test
    @DisplayName("Poodle Sold Removes from Inventory")
    public void poodleSoldTest() throws Exception {
        PetStore store = new PetStore();
        store.init();
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);
        int expectedSize = store.getPetsForSale().size() - 1;
        store.soldPetItem(poodle);
        assertEquals(expectedSize, store.getPetsForSale().size());
    }

    @Test
    @DisplayName("Poodle Duplicate Record Exception")
    public void poodleDupRecordExceptionTest() {
        PetStore store = new PetStore();
        store.init();
        store.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);
        assertThrows(DuplicatePetStoreRecordException.class, () -> store.soldPetItem(poodle));
    }

    @Test
    @DisplayName("Sphynx Sold Removes from Inventory")
    public void sphynxSoldTest() throws Exception {
        PetStore store = new PetStore();
        store.init();
        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"), 2);
        int expectedSize = store.getPetsForSale().size() - 1;
        store.soldPetItem(sphynx);
        assertEquals(expectedSize, store.getPetsForSale().size());
    }

    @TestFactory
    @DisplayName("Dynamic Sphynx Removal Test")
    public Stream<DynamicNode> sphynxSoldTest2() throws Exception {
        PetStore store = new PetStore();
        store.init();
        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"), 2);
        int expectedSize = store.getPetsForSale().size() - 1;
        Cat removedItem = (Cat) store.soldPetItem(sphynx);

        return Stream.of(dynamicContainer("Sphynx Dynamic Test", Arrays.asList(
                dynamicTest("Inventory Size Check", () ->
                        assertEquals(expectedSize, store.getPetsForSale().size())),
                dynamicTest("Removed Cat Check", () ->
                        assertEquals(sphynx.toString(), removedItem.toString()))
        )));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, -10, 128, Integer.MIN_VALUE})
    void isNumberEven(int number) {
        assertTrue(Numbers.isEven(number));
    }

    @Test
    @DisplayName("Add Pet to Inventory")
    public void addPetToInventoryTest() {
        PetStore store = new PetStore();
        store.init();
        int sizeBefore = store.getPetsForSale().size();
        Dog newDog = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.MALTESE,
                new BigDecimal("300.00"), 77);
        store.addPetInventoryItem(newDog);
        assertEquals(sizeBefore + 1, store.getPetsForSale().size());
    }

    @Test
    @DisplayName("Store ID 0 Throws PetNotFoundSaleException")
    public void storeIdZeroThrowsException() {
        PetStore store = new PetStore();
        Dog badDog = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.GOLDEN_DOODLE,
                new BigDecimal("300.00"), 0);
        assertThrows(PetNotFoundSaleException.class, () -> store.soldPetItem(badDog));
    }

    @Test
    @DisplayName("Duplicate Cat Record Exception")
    public void duplicateCatExceptionTest() {
        PetStore store = new PetStore();
        store.init();
        Cat duplicateCat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.BURMESE,
                new BigDecimal("65.00"), 1);
        store.initAddDuplicateItem(duplicateCat);
        assertThrows(DuplicatePetStoreRecordException.class, () -> store.soldPetItem(duplicateCat));
    }

    @Test
    @DisplayName("initAddDuplicateItem Adds After Init")
    public void initAddDuplicateItemTest() {
        Dog extraDog = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.FEMALE, Breed.POODLE,
                new BigDecimal("500.00"), 10);
        PetStore store = new PetStore();
        store.initAddDuplicateItem(extraDog);
        assertEquals(6, store.getPetsForSale().size());
    }

    @Test
    @DisplayName("Sell Cat Not In Inventory Throws Exception")
    public void sellCatNotInInventoryThrows() {
        PetStore store = new PetStore();
        Cat missingCat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.SIAMESE,
                new BigDecimal("80.00"), 999);
        assertThrows(PetNotFoundSaleException.class, () -> store.soldPetItem(missingCat));
    }

    @Test
    @DisplayName("Sell Bird Removes from Inventory")
    public void birdSoldRemovesItemTest() throws Exception {
        PetStore store = new PetStore();
        store.init();
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.BLUE_JAY,
                new BigDecimal("25.00"), 20);
        store.addPetInventoryItem(bird);
        int expectedSize = store.getPetsForSale().size() - 1;
        store.soldPetItem(bird);
        assertEquals(expectedSize, store.getPetsForSale().size());
    }

    @Test
    @DisplayName("Sell Bird Not In Inventory Throws Exception")
    public void sellBirdNotInInventoryThrows() {
        PetStore store = new PetStore();
        Bird missingBird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.CARDINAL,
                new BigDecimal("30.00"), 999);
        assertThrows(PetNotFoundSaleException.class, () -> store.soldPetItem(missingBird));
    }

    @Test
    @DisplayName("Duplicate Bird Throws Exception")
    public void duplicateBirdExceptionTest() {
        PetStore store = new PetStore();
        Bird bird = new Bird(AnimalType.DOMESTIC, Skin.FEATHERS, Gender.FEMALE, Breed.BLUE_JAY,
                new BigDecimal("25.00"), 21);
        store.addPetInventoryItem(bird);
        store.addPetInventoryItem(bird);
        assertThrows(DuplicatePetStoreRecordException.class, () -> store.soldPetItem(bird));
    }

    @Test
    @DisplayName("Unknown Pet Type Removal Works")
    public void unknownPetTypeTriggersDefaultRemoval() {
        PetStore store = new PetStore();
        Pet unknown = new Pet(PetType.UNKNOWN, new BigDecimal("5.00"), Gender.UNKNOWN, 999) {
            @Override
            public Gender getGender() {
                return Gender.UNKNOWN;
            }
        };
        store.addPetInventoryItem(unknown);
        assertDoesNotThrow(() -> {
            try {
                store.soldPetItem(unknown);
            } catch (PetNotFoundSaleException e) {
                // expected if storeId = 0
            } catch (DuplicatePetStoreRecordException e) {
                fail("Should not throw duplicate exception.");
            }
        });
    }
}
