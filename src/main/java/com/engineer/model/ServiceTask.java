package com.engineer.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Konrad on 03.01.2016.
 */

@Entity
@Table(name = "ServiceTask")
public class ServiceTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_task_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "date_in")
    private Date dateCreate;

    @Column(name = "date_out")
    private Date dateFinish;

    public ServiceTask() {

    }

    public ServiceTask(User user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }


    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }
}
