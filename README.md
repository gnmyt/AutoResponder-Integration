[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache-2.0 License][license-shield]][license-url]



<br />
<p align="center">
  <a href="https://github.com/gnmyt/AutoResponder-Integration">
    <img src="https://i.imgur.com/a8DOutI.png" alt="Logo" width="80" height="80">
  </a>
</p>
<h3 align="center">AutoResponder Integration</h3>

## About The Project

An unofficial java integration for the AutoResponder App. Use it to create a more professional and complex bot


## Setup guide
If you want to set this up in the AutoResponder app, you need to follow our [setup guide](SETUP.md)

### Installation

1. Add the jitpack repository to your `pom.xml`
   ```xml
   <repositories>
      <repository>
         <id>jitpack.io</id>
         <url>https://jitpack.io</url>
      </repository>
   </repositories>
   ```
2. Add the dependency to your `pom.xml`
   ```xml
   <dependency>
	    <groupId>com.github.gnmyt</groupId>
	    <artifactId>AutoResponder-Integration</artifactId>
	    <version>1.0.0</version>
   </dependency>
   ```

### Usage Examples

#### 1. Creating a new auto responder instance

```java
SimpleAutoResponder autoResponder = new SimpleAutoResponder();
```

#### 2. Setting up the responder

1. If you want to you can set the port of the server
   ```java
   autoResponder.setPort(8025);
   ```
2. You can also set a prefix of the commands
   ```java
   autoResponder.setPrefix("!");
   ```
3. You want to secure the webserver? No problem. Just set an authentication and don't forget to set it in the responder
   app!
   ```java
   autoResponder.useAuthentication("username", "password");
   ```
4. Now lets start the integration
   ```java
   autoResponder.start();
   ```

#### 3. Custom Handlers

1. Using the random error handler. It sends random messages from a list if the message could not be found
   ```java
   autoResponder.useNotFoundHandler(new RandomErrorHandler("Error message 1", "Error message 2", "Error message 3"));
   ```
2. (Default) Doing nothing. Just ignoring the not found messages. It sends a random replies array back to the responder
   ```java
   autoResponder.useNotFoundHandler(new SendNothingHandler());
   ```
3. Using the simple error handler. It sends all messages from a list if the messages could not be found.
   ```java
   autoResponder.useNotFoundHandler(new SendErrorHandler("First message", "Second message", "Third message"));
   ```

You can find a full example of that in the [CustomHandlersExample](src/examples/java/CustomHandlersExample.java)

#### 4. Dialogflow Integration

Do you want to integrate Dialogflow into your project? No problem. If you want to only let dialogflow handle the message
if it could not be found, take a look at the step above. You like to do it your own? No problem. Just create a new
DialogflowIntegration class.

   ```java
   DialogflowIntegration integration=new DialogflowIntegration(new File("your_credentials_file.json"));
   integration.setLanguageCode("de"); // Here you can set the language of the integration
   integration.getMessage("My message","The sender of the message / a session id for better training");
   ```

#### 5. Listeners

This project supports listeners. That means you can create your own, custom events that happen after the responder sent
us something. You can use the `ChatMessageReceivedEvent` or the `GroupMessageReceivedEvent` for that. If you want to
learn more, check out the [ListenerExample](src/examples/java/ListenerExample.java)

#### 6. Commands

This project also supports commands. That means that you create your own custom commands that happen after the responder
sent us the command. You can use the `ResponderGroupCommand` or the `ResponderChatCommand` to handle those commands. If
you want to learn more, check out the [CommandExample](src/examples/java/CommandExample.java)

#### 7. Secondary use
You use the AutoResponder app as main place of your rules, any you only want to react to all commands? No problem. Just
create a new rule and set your command prefix before the "*". The responder will thn only react if the message is a command  

## Other examples

You can find some other examples in the [examples directory](src/examples/java).

## License

Distributed under the Apache-2.0 License. See `LICENSE` for more information.

## End

Currently, there are not many features, so feel free to write me some suggestions!

[contributors-shield]: https://img.shields.io/github/contributors/gnmyt/AutoResponder-Integration.svg?style=for-the-badge

[contributors-url]: https://github.com/gnmyt/AutoResponder-Integration/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/gnmyt/AutoResponder-Integration.svg?style=for-the-badge

[forks-url]: https://github.com/gnmyt/AutoResponder-Integration/network/members

[stars-shield]: https://img.shields.io/github/stars/gnmyt/AutoResponder-Integration.svg?style=for-the-badge

[stars-url]: https://github.com/gnmyt/AutoResponder-Integration/stargazers

[issues-shield]: https://img.shields.io/github/issues/gnmyt/AutoResponder-Integration.svg?style=for-the-badge

[issues-url]: https://github.com/gnmyt/AutoResponder-Integration/issues

[license-shield]: https://img.shields.io/github/license/gnmyt/AutoResponder-Integration.svg?style=for-the-badge

[license-url]: https://github.com/gnmyt/AutoResponder-Integration/blob/master/LICENSE