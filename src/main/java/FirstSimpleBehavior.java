import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class FirstSimpleBehavior extends AbstractBehavior<String> {


    private FirstSimpleBehavior(ActorContext<String> context) {
        super(context);
    }

    // return FirstSimpleBehavior::new means ->
    // return Behaviors.setup(context ->{
    //      return new FirstSimpleBehavior(context);
    //  });
    public static Behavior<String> create(){
        return Behaviors.setup(FirstSimpleBehavior::new);
    }

    //when they receive a message
    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                //put who are you
                .onMessageEquals("create a child", () -> {

                    //create a new actor (this will be a child actor)
                    ActorRef<String> secondActor = getContext().spawn(FirstSimpleBehavior.create(), "secondActor");
                    secondActor.tell("who are you?");
                    return this;

                })
                .onAnyMessage(message ->{
                    System.out.println("I received the message : " + message);
                    return this;
                })
                .build();
    }


}
