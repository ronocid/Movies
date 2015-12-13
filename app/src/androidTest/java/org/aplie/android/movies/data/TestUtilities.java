package org.aplie.android.movies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

public class TestUtilities extends AndroidTestCase {
    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static ContentValues createStarWarMovieValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, "\\/upT2I74D4qHXjzXERxLQZQRRNbX.jpg");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, "Séptima entrega de la saga Star Wars. Fue confirmada en octubre de 2012, cuando The Walt Disney Company compró LucasFilms por 4.000 millones de dólares. El guionista de 'El imperio contraataca', Lawrence Kasdan, repite en esta octava entrega de la franquicia intergaláctica más conocida en la historia del cine con Han Solo, Leia y Luke Skywalker a la cabeza. 'La guerra de las galaxias: Episodio VIII' se sitúa en la Era de la Nueva República. Veinte años después de haber terminado la historia de la trilogía original, los grupos políticos siguen intentando reconstruir un gobierno con el resurgir de la Nueva República. La fuerza también siente el cambio y los Maestros Jedi saben que es el momento de regresar.");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, "Star Wars: Episode VII - The Force Awakens");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, "Star Wars. Episode VII: El despertar de la Fuerza");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH, "\\/njv65RTipNSTozFLuF85jL0bcQe.jpg");
        testValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, 7.11);

        return testValues;
    }
}
