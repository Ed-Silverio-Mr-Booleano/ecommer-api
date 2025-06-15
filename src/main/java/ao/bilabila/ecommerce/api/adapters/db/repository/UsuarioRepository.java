package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.Usuario;
import ao.bilabila.ecommerce.api.ports.out.IUsuarioRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class UsuarioRepository implements IUsuarioRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveUsuario(Usuario usuario) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO usuario (username, password, email, data_criacao, role) VALUES (?, ?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getEmail());
            ps.setTimestamp(4, usuario.getCreatedAt() != null ? usuario.getCreatedAt() : new Timestamp(System.currentTimeMillis()));
            ps.setString(5, usuario.getRole() != null ? usuario.getRole() : "USER");
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Usuario findByUsername(String username) {
        String sql = "SELECT id, username, password, email, data_criacao, role FROM usuario WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> mapRowToUsuario(rs));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        String sql = "SELECT id, username, password, email, data_criacao, role FROM usuario WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> mapRowToUsuario(rs));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Usuario> findAllUsersByRole(String role) {
        String sql = "SELECT id, username, password, email, data_criacao, role FROM usuario WHERE role = ?";
        return jdbcTemplate.query(sql, new Object[]{role}, (rs, rowNum) -> mapRowToUsuario(rs));
    }

    private Usuario mapRowToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));
        usuario.setCreatedAt(rs.getTimestamp("data_criacao"));
        usuario.setRole(rs.getString("role"));
        return usuario;
    }
}