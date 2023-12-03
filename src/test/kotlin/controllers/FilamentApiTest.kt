package controllers
import models.Filament

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import persistence.XMLSerializer
import java.io.File

class FilamentApiTest {

    private var eSun: Filament? = null
    private var flashForge: Filament? = null
    private var ninjaFlex: Filament? = null
    private var polyMaker: Filament? = null
    private var eryone: Filament? = null
    private var populatedFilaments: FilamentApi? = FilamentApi(XMLSerializer(File("filaments.xml")))
    private var emptyFilaments: FilamentApi? = FilamentApi(XMLSerializer(File("filaments.xml")))

    @BeforeEach
    fun setup(){
        eSun = Filament(0, "eSun", "PLA+", "Black",1)
        flashForge = Filament(1,"flashForge", "PLA", "Orange", 2)
        ninjaFlex = Filament(2,"ninjaFlex", "TPU", "White", 1)
        polyMaker = Filament(3,"polyMaker", "ABS", "Brown", 1)
        eryone = Filament(4,"eryone", "PETG", "Blue", 1)

        //adding 5 Filament to the filaments api
        populatedFilaments!!.add(eSun!!)
        populatedFilaments!!.add(flashForge!!)
        populatedFilaments!!.add(ninjaFlex!!)
        populatedFilaments!!.add(polyMaker!!)
        populatedFilaments!!.add(eryone!!)
    }

    @AfterEach
    fun tearDown(){
        eSun = null
        flashForge = null
        ninjaFlex = null
        polyMaker = null
        eryone = null
        populatedFilaments = null
        emptyFilaments = null
    }

    @Nested
    inner class AddFilaments {
        @Test
        fun `adding a filament to a populated list adds to ArrayList`() {
            val newFilament = Filament(0,"creality", "PLA", "Purple", 3)
            assertEquals(5, populatedFilaments!!.numberOfFilaments())
            assertTrue(populatedFilaments!!.add(newFilament))
            assertEquals(6, populatedFilaments!!.numberOfFilaments())
            assertEquals(newFilament, populatedFilaments!!.findFilament(populatedFilaments!!.numberOfFilaments() - 1))
        }

        @Test
        fun `adding a Filament to an empty list adds to ArrayList`() {
            val newFilament = Filament(0,"sunlu", "ASA", "Green",1)
            assertEquals(0, emptyFilaments!!.numberOfFilaments())
            assertTrue(emptyFilaments!!.add(newFilament))
            assertEquals(1, emptyFilaments!!.numberOfFilaments())
            assertEquals(newFilament, emptyFilaments!!.findFilament(emptyFilaments!!.numberOfFilaments() - 1))
        }
    }

    @Nested
    inner class ListFilaments {

        @Test
        fun `listAllFilaments returns No Filaments Stored message when ArrayList is empty`() {
            assertEquals(0, emptyFilaments!!.numberOfFilaments())
            assertTrue(emptyFilaments!!.listAllFilaments().contains("no filaments stored"))
        }

        @Test
        fun `listAllFilaments returns filaments when ArrayList has filaments stored`() {
            assertEquals(5, populatedFilaments!!.numberOfFilaments())
            val filamentsString = populatedFilaments!!.listAllFilaments()
            assertTrue(filamentsString.contains("e-Sun"))
            assertTrue(filamentsString.contains("flash-Forge"))
            assertTrue(filamentsString.contains("ninja-Flex"))
            assertTrue(filamentsString.contains("poly-Maker"))
            assertTrue(filamentsString.contains("Eryone"))
        }
    }

    //@Nested
    //inner class DeleteFilaments {

      //  @Test
        //fun `deleting a Filament that does not exist, returns null`() {
           // assertNull(emptyFilaments!!.delete(0))
            //assertNull(populatedFilaments!!.delete(-1))
            //assertNull(populatedFilaments!!.delete(5))
        //}

        //@Test
        //fun `deleting a Filament that exists deletes and returns deleted object`() {
           // assertEquals(5, populatedFilaments!!.numberOfFilaments())
          //  assertEquals(eryone, populatedFilaments!!.deleteFilament(4))
          //  assertEquals(4, populatedFilaments!!.numberOfFilaments())
          //  assertEquals(eSun, populatedFilaments!!.deleteFilament(0))
          //  assertEquals(3, populatedFilaments!!.numberOfFilaments())

       // }
   // }

    @Nested
    inner class UpdateFilaments {
        @Test
        fun `updating a filament that does not exist returns false`(){
            assertFalse(populatedFilaments!!.update(6, Filament(6,"colorFabb",  "Nylon","Jade Green",3)))
            assertFalse(populatedFilaments!!.update(-1, Filament(-1,"Hatchbox", "Flex", "Red", 1)))
            assertFalse(emptyFilaments!!.update(0, Filament(0,"MatterHackers", "PVA", "Cyan",2 )))
        }

        @Test
        fun `updating a filament that exists returns true and updates`() {
            //check filament 5 exists and check the contents
            assertEquals(eryone, populatedFilaments!!.findFilament(4))
            assertEquals("eryone", populatedFilaments!!.findFilament(4)!!.filamentBrand)
            assertEquals("ABS", populatedFilaments!!.findFilament(3)!!.filamentType)
            assertEquals("Blue", populatedFilaments!!.findFilament(4)!!.filamentColor)

            //update filament 5 with new information and ensure contents updated successfully
            assertTrue(populatedFilaments!!.update(4, Filament(4, "RepRap", "TPU", "Blue",1)))
            assertEquals("RepRap", populatedFilaments!!.findFilament(4)!!.filamentBrand)
            assertEquals("TPU", populatedFilaments!!.findFilament(2)!!.filamentType)
            assertEquals("Blue", populatedFilaments!!.findFilament(4)!!.filamentColor)
        }
    }

}