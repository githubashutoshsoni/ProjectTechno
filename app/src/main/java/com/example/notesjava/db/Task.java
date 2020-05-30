package com.example.notesjava.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Task {

    @PrimaryKey
    @NonNull
    public String mId;
    @ColumnInfo(name = "title")
    public String mTitle;
    @ColumnInfo(name = "description")
    public String mDescription;
    @ColumnInfo(name = "completed")
    public boolean mCompleted;

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public boolean ismCompleted() {
        return mCompleted;
    }


    @Ignore
    public Task(@Nullable String title, @Nullable String description) {
        this(title, description, UUID.randomUUID().toString(), false);
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @NonNull String id) {
        this(title, description, id, false);
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this(title, description, UUID.randomUUID().toString(), completed);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(mId, task.mId) &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mDescription);
    }

    public Task(@Nullable String title, @Nullable String description,
                @NonNull String id, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

}
