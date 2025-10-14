package com.fiap.mariacomanda.core.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OpeningHoursTest {

    @Test
    void construtorDeveLancarExcecaoQuandoDaysOfWeekForNuloOuVazio() {
        LocalTime open = LocalTime.of(9, 0);
        LocalTime close = LocalTime.of(18, 0);

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () ->
                new OpeningHours(null, open, close)
        );
        assertEquals("Days of week cannot be null or empty", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () ->
                new OpeningHours(Set.of(), open, close)
        );
        assertEquals("Days of week cannot be null or empty", ex2.getMessage());
    }

    @Test
    void construtorDeveLancarExcecaoQuandoOpenTimeForNulo() {
        Set<DayOfWeek> dias = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
        LocalTime close = LocalTime.of(18, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new OpeningHours(dias, null, close)
        );
        assertEquals("Open time cannot be null", ex.getMessage());
    }

    @Test
    void construtorDeveLancarExcecaoQuandoCloseTimeForNulo() {
        Set<DayOfWeek> dias = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
        LocalTime open = LocalTime.of(8, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new OpeningHours(dias, open, null)
        );
        assertEquals("Close time cannot be null", ex.getMessage());
    }

    @Test
    void construtorDeveLancarExcecaoQuandoOpenTimeNaoForAntesDeCloseTime() {
        Set<DayOfWeek> dias = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
        LocalTime open = LocalTime.of(18, 0);
        LocalTime close = LocalTime.of(9, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new OpeningHours(dias, open, close)
        );
        assertEquals("Open time must be before close time", ex.getMessage());
    }

    @Test
    void fromStringDeveLancarExcecaoQuandoStringForNulaOuVazia() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () ->
                OpeningHours.fromString(null)
        );
        assertEquals("Opening hours string cannot be null or empty", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () ->
                OpeningHours.fromString("")
        );
        assertEquals("Opening hours string cannot be null or empty", ex2.getMessage());

        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () ->
                OpeningHours.fromString("   ")
        );
        assertEquals("Opening hours string cannot be null or empty", ex3.getMessage());
    }

    @Test
    void fromStringDeveCriarOpeningHoursParaSabadoEDomingo() {
        OpeningHours oh = OpeningHours.fromString("SAB-DOM:11:00-23:00");
        assertEquals(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), oh.daysOfWeek());
        assertEquals(LocalTime.of(11, 0), oh.openTime());
        assertEquals(LocalTime.of(23, 0), oh.closeTime());
    }

    @Test
    void fromStringDeveCriarOpeningHoursFormatoSimplesParaTodosOsDias() {
        OpeningHours oh = OpeningHours.fromString("10:00-22:00");
        assertEquals(Set.of(DayOfWeek.values()), oh.daysOfWeek());
        assertEquals(LocalTime.of(10, 0), oh.openTime());
        assertEquals(LocalTime.of(22, 0), oh.closeTime());
    }

    @Test
    void fromStringDeveLancarExcecaoParaFormatoInvalido() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                OpeningHours.fromString("ABCD")
        );
        assertEquals(
                "Restaurant opening hours must be in format HH:MM-HH:MM or SEG-SEX:HH:MM-HH:MM (e.g., '10:00-22:00' or 'SEG-SEX:10:00-22:00')",
                ex.getMessage()
        );
    }

    @Test
    void fromStringDeveLancarExcecaoQuandoNaoTiverDuasPartesSeparadasPorHifen() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () ->
                OpeningHours.fromString("10:00") 
        );
        assertEquals("Restaurant opening hours must be in format HH:MM-HH:MM or SEG-SEX:HH:MM-HH:MM (e.g., '10:00-22:00' or 'SEG-SEX:10:00-22:00')", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () ->
                OpeningHours.fromString("10:00-22:00-23:30") // três partes
        );
        assertEquals("Restaurant opening hours must be in format HH:MM-HH:MM or SEG-SEX:HH:MM-HH:MM (e.g., '10:00-22:00' or 'SEG-SEX:10:00-22:00')", ex2.getMessage());
    }

    @Test
    void toDisplayString_deveRetornarCorretoParaTodosOsDias() {
        OpeningHours oh = new OpeningHours(Set.of(DayOfWeek.values()), LocalTime.of(9, 0), LocalTime.of(18, 0));
        assertEquals("Seg-Dom: 09:00-18:00", oh.toDisplayString());
    }

    @Test
    void toDisplayString_deveRetornarCorretoParaSegundaASexta() {
        OpeningHours oh = new OpeningHours(
            Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
            LocalTime.of(10, 0),
            LocalTime.of(22, 0)
        );
        assertEquals("Seg-Sex: 10:00-22:00", oh.toDisplayString());
    }

    @Test
    void toDisplayString_deveRetornarCorretoParaSabadoEDomingo() {
        OpeningHours oh = new OpeningHours(
            Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            LocalTime.of(11, 0),
            LocalTime.of(23, 0)
        );
        assertEquals("Sáb-Dom: 11:00-23:00", oh.toDisplayString());
    }

    @Test
    void toDisplayString_deveRetornarSomenteHorasParaDiasPersonalizados() {
        OpeningHours oh = new OpeningHours(
            Set.of(DayOfWeek.MONDAY, DayOfWeek.SATURDAY),
            LocalTime.of(15, 0),
            LocalTime.of(20, 0)
        );
        assertEquals("15:00-20:00", oh.toDisplayString());
    }

    @Test
    void toStorageString_deveRetornarSEGDOM_ParaTodosOsDias() {
        OpeningHours oh = new OpeningHours(Set.of(DayOfWeek.values()), LocalTime.of(9, 0), LocalTime.of(18, 0));
        assertEquals("SEG-DOM:09:00-18:00", oh.toStorageString());
    }

    @Test
    void toStorageString_deveRetornarSEGSEX_ParaSegundaASexta() {
        OpeningHours oh = new OpeningHours(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                LocalTime.of(10, 0), LocalTime.of(22, 0)
        );
        assertEquals("SEG-SEX:10:00-22:00", oh.toStorageString());
    }

    @Test
    void toStorageString_deveRetornarSABDOM_ParaSabadoEDomingo() {
        OpeningHours oh = new OpeningHours(
                Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                LocalTime.of(11, 0), LocalTime.of(23, 0)
        );
        assertEquals("SAB-DOM:11:00-23:00", oh.toStorageString());
    }

    @Test
    void toStorageString_deveRetornarApenasHorasParaDiasPersonalizados() {
        OpeningHours oh = new OpeningHours(
                Set.of(DayOfWeek.TUESDAY, DayOfWeek.SUNDAY),
                LocalTime.of(12, 0), LocalTime.of(14, 0)
        );
        assertEquals("12:00-14:00", oh.toStorageString());
    }

    @Test
    void parseDaysDeveRetornarCorretoParaSEG_SEX() throws Exception {
        var method = OpeningHours.class.getDeclaredMethod("parseDays", String.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<DayOfWeek> result = (Set<DayOfWeek>) method.invoke(null, "SEG-SEX");
        assertEquals(Set.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY), result);
    }

    @Test
    void parseDaysDeveRetornarCorretoParaSAB_DOM() throws Exception {
        var method = OpeningHours.class.getDeclaredMethod("parseDays", String.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<DayOfWeek> result = (Set<DayOfWeek>) method.invoke(null, "SAB-DOM");
        assertEquals(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), result);
    }

    @Test
    void parseDaysDeveRetornarCorretoParaSEG_DOM() throws Exception {
        var method = OpeningHours.class.getDeclaredMethod("parseDays", String.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<DayOfWeek> result = (Set<DayOfWeek>) method.invoke(null, "SEG-DOM");
        assertEquals(Set.of(DayOfWeek.values()), result);
    }

    @Test
    void parseDaysDeveLancarExcecaoParaInvalido() throws Exception {
        var method = OpeningHours.class.getDeclaredMethod("parseDays", String.class);
        method.setAccessible(true);
        Exception ex = assertThrows(Exception.class, () -> method.invoke(null, "FOO-BAR"));
        assertTrue(ex.getCause() instanceof IllegalArgumentException);
        assertEquals("Invalid days format. Use SEG-SEX, SAB-DOM, or SEG-DOM", ex.getCause().getMessage());
    }
}
