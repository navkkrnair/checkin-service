package com.ams.checkin.component;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(CheckInSource.class)
public class Sender
{

	/*@Output(CheckInSource.CHECKINQ)
	@Autowired
	private MessageChannel channel;

	public void send(Object message)
	{
		channel.send(MessageBuilder.withPayload(message)
		                           .build());
	}*/
}