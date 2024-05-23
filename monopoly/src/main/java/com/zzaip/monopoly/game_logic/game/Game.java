package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.StartField;
import com.zzaip.monopoly.game_logic.player.Player;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "game")
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private int roundCount;
    private int roundLimit;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @ToString.Exclude
    private List<Player> players;

    @ElementCollection
    @CollectionTable(name = "players_queue", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "player_name")
    private List<String> playersQueue;

    @ManyToOne
    @JoinColumn(name = "current_player_id")
    private Player currentPlayer;

    private String myPlayerName;
    private String winnerPlayerName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @ToString.Exclude
    private List<Field> board;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_field_id")
    private StartField startField;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Game game = (Game) o;
        return gameId != 0 && Objects.equals(gameId, game.gameId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
