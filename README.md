## Cool Button example

An example that demonstrates how to use third-party JavaScript component in Wicket application.

This code example was written for my blog post (in Russian) here:

[http://www.shafranov.net/blog/2012/05/13/wicket-javascript/](http://www.shafranov.net/blog/2012/05/13/wicket-javascript/)

## Dependencies

Project was developed using Apache Wicket 1.5.4.

## Usage

If you want to see how everything really works then you should:

1. add source code to your Wicket application (for example, as utility project),
2. mount CoolButtonPage class in your application's class init() method:

    mountPage("/cool-button", CoolButtonPage.class);

## License

This example is licensed under the [MIT License](/shafranov/wicket-javascript/blob/master/LICENSE).