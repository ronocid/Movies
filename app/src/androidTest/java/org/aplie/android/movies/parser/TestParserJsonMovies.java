package org.aplie.android.movies.parser;

import android.test.AndroidTestCase;

import org.aplie.android.movies.utils.Movie;

import java.util.List;

public class TestParserJsonMovies extends AndroidTestCase{

    public static final int SIZE_MOVIE_JSON = 2;

    public void testParserJson(){
        String testStringJson = "{\"page\":1,\"results\":[{\"poster_path\":\"\\/nXJt0JZ0UXxwPBcVj2KJ19bUBQ6.jpg\",\"adult\":false,\"overview\":\"Armado con la asombrosa capacidad de reducir su tamaño a la dimensiones de un insecto, el estafador Scott Lang (Paul Rudd) debe sacar a relucir al héroe que lleva dentro y ayudar a su mentor, el doctor Hank Pym (Michael Douglas), a proteger de una nueva generación de amenazas el secreto que se esconde tras el traje de Ant-Man, con un casco que le permite comunicarse con las hormigas. A pesar de los obstáculos aparentemente insuperables que les acechan, Pym y Lang deben planear y llevar a cabo un atraco para intentar salvar al mundo.\",\"release_date\":\"2015-07-17\",\"genre_ids\":[878,28,12],\"id\":102899,\"original_title\":\"Ant-Man\",\"original_language\":\"en\",\"title\":\"Ant-Man\",\"backdrop_path\":\"\\/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg\",\"popularity\":52.456352,\"vote_count\":1931,\"video\":false,\"vote_average\":6.9},{\"poster_path\":\"\\/sv4UUyQxP3qCp7kArPhZk1JlAj8.jpg\",\"adult\":false,\"overview\":\"'Los juegos del hambre: Sinsajo. Parte 2' nos trae la impactante conclusión de la franquicia, en la que Katniss Everdeen (Jennifer Lawrence) se da cuenta de que ya no sólo está en juego su supervivencia, sino también el futuro. Con Panem sumida en una guerra a gran escala, Katniss tendrá que plantar cara al presidente Snow (Donald Sutherland) en el enfrentamiento final. Katniss, acompañada por un grupo de sus mejores amigos, que incluye a Gale (Liam Hemsworth), Finnick (Sam Claflin) y Peeta (Josh Hutcherson), emprende una misión con la unidad del Distrito 13, en la que arriesgan sus vidas para liberar a los ciudadanos de Panem y orquestan un intento de asesinato del presidente Snow, cada vez más obsesionado con destruirla. Las trampas mortales, los enemigos y las decisiones morales que aguardan a Katniss la pondrán en mayores aprietos que ninguna arena de Los Juegos del Hambre.\",\"release_date\":\"2015-11-19\",\"genre_ids\":[18,12,28],\"id\":131634,\"original_title\":\"The Hunger Games: Mockingjay - Part 2\",\"original_language\":\"en\",\"title\":\"Los juegos del hambre: Sinsajo. Parte 2\",\"backdrop_path\":\"\\/qjn3fzCAHGfl0CzeUlFbjrsmu4c.jpg\",\"popularity\":25.815282,\"vote_count\":605,\"video\":false,\"vote_average\":7.09}],\"total_results\":247943,\"total_pages\":12398}";
        List<Movie> listMovies = PaserJsonMovies.parserJson(testStringJson);

        assertEquals("Error: la lista no contiene todos los elementos", SIZE_MOVIE_JSON, listMovies.size());

        Movie movie1 = listMovies.get(0);
        Movie movie2 = listMovies.get(1);

        checkMovie1(movie1);
        checkMovie2(movie2);
    }

    private void checkMovie2(Movie movie2) {
        assertEquals("Error: testParserJson movie2 title", "Los juegos del hambre: Sinsajo. Parte 2", movie2.getTitle());
        assertEquals("Error: testParserJson movie2 original title", "The Hunger Games: Mockingjay - Part 2", movie2.getOriginaTitle());
        assertEquals("Error: testParserJson movie2 poster Path", "/sv4UUyQxP3qCp7kArPhZk1JlAj8.jpg", movie2.getPosterPath());
        assertEquals("Error: testParserJson movie2 overview", "'Los juegos del hambre: Sinsajo. Parte 2' nos trae la impactante conclusión de la franquicia, en la que Katniss Everdeen (Jennifer Lawrence) se da cuenta de que ya no sólo está en juego su supervivencia, sino también el futuro. Con Panem sumida en una guerra a gran escala, Katniss tendrá que plantar cara al presidente Snow (Donald Sutherland) en el enfrentamiento final. Katniss, acompañada por un grupo de sus mejores amigos, que incluye a Gale (Liam Hemsworth), Finnick (Sam Claflin) y Peeta (Josh Hutcherson), emprende una misión con la unidad del Distrito 13, en la que arriesgan sus vidas para liberar a los ciudadanos de Panem y orquestan un intento de asesinato del presidente Snow, cada vez más obsesionado con destruirla. Las trampas mortales, los enemigos y las decisiones morales que aguardan a Katniss la pondrán en mayores aprietos que ninguna arena de Los Juegos del Hambre.", movie2.getOverview());
        assertEquals("Error: testParserJson movie2 backdrop path", "/qjn3fzCAHGfl0CzeUlFbjrsmu4c.jpg", movie2.getBackdropPath());
        assertEquals("Error: testParserJson movie2 vote average", 7.09, movie2.getVoteAverage());
    }

    private void checkMovie1(Movie movie1) {
        assertEquals("Error: testParserJson movie1 title","Ant-Man",movie1.getTitle());
        assertEquals("Error: testParserJson movie1 original title","Ant-Man",movie1.getOriginaTitle());
        assertEquals("Error: testParserJson movie1 poster Path","/nXJt0JZ0UXxwPBcVj2KJ19bUBQ6.jpg",movie1.getPosterPath());
        assertEquals("Error: testParserJson movie1 overview","Armado con la asombrosa capacidad de reducir su tamaño a la dimensiones de un insecto, el estafador Scott Lang (Paul Rudd) debe sacar a relucir al héroe que lleva dentro y ayudar a su mentor, el doctor Hank Pym (Michael Douglas), a proteger de una nueva generación de amenazas el secreto que se esconde tras el traje de Ant-Man, con un casco que le permite comunicarse con las hormigas. A pesar de los obstáculos aparentemente insuperables que les acechan, Pym y Lang deben planear y llevar a cabo un atraco para intentar salvar al mundo.",movie1.getOverview());
        assertEquals("Error: testParserJson movie1 backdrop path","/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg",movie1.getBackdropPath());
        assertEquals("Error: testParserJson movie1 vote average",6.9,movie1.getVoteAverage());
    }
}
