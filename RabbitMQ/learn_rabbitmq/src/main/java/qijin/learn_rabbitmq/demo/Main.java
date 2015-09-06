
package qijin.learn_rabbitmq.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rabbitmq.client.ConnectionFactory;

public class Main
{
    public static void main(final String[] args) throws Exception
    {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("cdc_api");
        factory.setPassword("admin");
        factory.setVirtualHost("ccm_dev_host");
        factory.setHost("192.168.148.130");
        factory.setPort(5672);

        // simulate dependency management creation and wiring
        final RabbitMqManager rabbitMqManager = new RabbitMqManager(factory);
        rabbitMqManager.start();

        final UserMessageManagerWithTopics userMessageManager = new UserMessageManagerWithTopics();
        userMessageManager.rabbitMqManager = rabbitMqManager;
        userMessageManager.onApplicationStart();

        final long maxUserId = 12;
        System.out.printf("开始模拟 %d个并发用户%n", maxUserId);
        final List<Thread> threads = new ArrayList<>();
        for (long userId = 1; userId <= maxUserId; userId++)
        {
            final Thread thread = new Thread(new UserSimulator(userId, maxUserId, userMessageManager),
                "user-thread-" + userId);
            threads.add(thread);
            thread.start();

            Thread.sleep(2500L);
        }

        // clean shutdown
        System.out.println("运行中， 按回车停止程序!");
        try (Scanner s = new Scanner(System.in))
        {
            s.nextLine();
        }

        for (final Thread thread : threads)
        {
            thread.interrupt();
            thread.join();//Suspend the main thread to wait the completion of this UserSimulator thread.
        }

        System.out.print("停止中...");
        rabbitMqManager.stop();
        System.out.print("再见!");
        System.exit(0);
    }
}
