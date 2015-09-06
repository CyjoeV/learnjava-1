
package qijin.learn_rabbitmq.demo;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public interface ChannelCallable<T>
{
    String getDescription();

    T call(Channel channel) throws IOException;
}
