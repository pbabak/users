package cz.morosystems.users.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(schema = "public", name="users")
@SequenceGenerator(name = "users_seq_generator", sequenceName = "users_seq", allocationSize = 1)
public class UserVO {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_generator")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 50, unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
