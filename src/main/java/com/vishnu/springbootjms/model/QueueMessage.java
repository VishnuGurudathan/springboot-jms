package com.vishnu.springbootjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * POJO class for Message
 *
 * @author : vishnu.g
 * created on : 26/Aug/2020
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueMessage implements Serializable {
    static final long serialVersionUID = -6703826490277916847L;

    private UUID id;
    private String message;
    @Builder.Default
    private Date date = new Date();
}
