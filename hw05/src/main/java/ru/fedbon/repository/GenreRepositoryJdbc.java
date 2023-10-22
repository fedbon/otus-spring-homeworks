package ru.fedbon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.fedbon.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        var count = jdbc.getJdbcOperations().queryForObject("select count(*) from genres", Long.class);
        return Objects.isNull(count) ? 0 : count;
    }

    @Override
    public Genre insert(Genre genre) {
        var params = new MapSqlParameterSource(Map.of("genre", genre.getGenreName()));
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres(genre) values(:genre)", params, keyHolder, new String[]{"id"});

        var key = keyHolder.getKey();

        if (key != null) {
            genre.setId(key.longValue());
        } else {
            throw new IllegalStateException("Failed to retrieve ID after insert operation");
        }
        return genre;
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genres set genre = :genre where id = :id",
                Map.of("genre", genre.getGenreName(), "id", genre.getId()));
    }

    @Override
    public Optional<Genre> findById(long id) {
        return jdbc.query("select id, genre from genres where id = :id",
                Map.of("id", id), new GenreRowMapper()).stream().findFirst();
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select id, genre from genres order by id", new GenreRowMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }

    @Override
    public long deleteAll() {
        return jdbc.getJdbcOperations().update("delete from genres");
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("genre"));
        }
    }
}
