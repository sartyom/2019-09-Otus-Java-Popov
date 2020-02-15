package ru.otus.hw15.web.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw15.api.model.User;
import ru.otus.hw15.common.Serializers;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.RequestHandler;
import ru.otus.hw15.web.service.FrontendService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetUserDataCollectionResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetUserDataCollectionResponseHandler.class);

  private final FrontendService frontendService;

  public GetUserDataCollectionResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      List<User> userData = Serializers.deserialize(msg.getPayload(), List.class);
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, List.class).ifPresent(consumer -> consumer.accept(userData));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
