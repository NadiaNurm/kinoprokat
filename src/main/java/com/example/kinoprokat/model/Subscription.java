package com.example.kinoprokat.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Data
public class Subscription {
    /*
        Модель подписки. Пользователь покупает подписку на неделю, месяц или год. У подписки есть жанр, дата начала, окончания в зависимости от типа и тип.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateStart;
    private LocalDate endDate;
    private Genres genres;
    @Enumerated(EnumType.STRING)
    private SubType subTypes;
    @ManyToOne
    private User user;

    public void setEndDate() {
        this.endDate = subscriptionEnd(this.subTypes);
    }

    public LocalDate subscriptionEnd(SubType subType) {
       /*
        Вычисляет дату окончания подписки в зависимости от ее типа.
        */
        Calendar calendar = Calendar.getInstance();
        switch (subType) {
            case week:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case month:
                calendar.add(Calendar.MONTH, 1);
                break;
            case year:
                calendar.add(Calendar.YEAR, 1);
                break;
            default:
                System.out.println("Oooops, something wrong !");
        }
        return LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(id, that.id) && Objects.equals(dateStart, that.dateStart) && Objects.equals(endDate, that.endDate) && genres == that.genres && subTypes == that.subTypes && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateStart, endDate, genres, subTypes, user);
    }
}
