package com.example.electivecourse.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 必修课课程
 *
 * @author admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class RequiredCourseDAO extends BaseCourseDAO {
    /**
     * 学分，有可能是小数
     */
    private Double credit;

    @Override
    public String toString() {
        return "Required course, "+super.toString() + "credit: %f.".formatted(credit);
    }
}
