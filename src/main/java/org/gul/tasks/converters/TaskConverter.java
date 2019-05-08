package org.gul.tasks.converters;

import org.gul.tasks.domain.Task;
import org.gul.tasks.dto.TaskDto;

public class TaskConverter {
    public static TaskDto toDto(Task task) {
        return new TaskDto(task.getId().toString(), task.getUpdated_at().toString(), task.getStatus().toString());
    }
}
