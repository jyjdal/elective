package com.example.electivecourse.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 选修课课程
 *
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class OptionalCourseDAO extends BaseCourseDAO {
    /**
     * 最大选修课程人数
     */
    private Integer maxStuNum;

    @Override
    public String toString() {
        return "Optional course, " +super.toString() + "max student number: %d.".formatted(maxStuNum);
    }
}
