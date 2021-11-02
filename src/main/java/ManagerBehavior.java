import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.math.BigInteger;
import java.util.Random;

public class ManagerBehavior extends AbstractBehavior<String> {

    private ManagerBehavior(ActorContext<String> context) {

        super(context);

    }

    public static Behavior<String> create() {

        return Behaviors.setup(ManagerBehavior::new);

    }

    @Override
    public Receive<String> createReceive() {

        return newReceiveBuilder()

                .onMessageEquals("start", () ->{

                   for(int i = 0; i < 20; i++){

                       //ActorSystem<String> actorSystem = ActorSystem.create(WorkerBehavior.create(), "WorkerBehavior"+i);
                       ActorRef<WorkerBehavior.Command> worker = getContext().spawn(WorkerBehavior.create(), "WorkerBehavior"+i);
                       worker.tell(new WorkerBehavior.Command("start", getContext().getSelf()));
                   }

                   return this;

                })
                .build();

    }

}
