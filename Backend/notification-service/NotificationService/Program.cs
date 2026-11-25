
using MongoDB.Driver;
using NotificationService.Health;
using NotificationService.Models;
using Steeltoe.Discovery.Eureka;

var builder = WebApplication.CreateBuilder(args);

// Controllers
builder.Services.AddControllers();

// Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// MongoDB configuration
var mongoConnectionString =
    builder.Configuration["MongoDb:ConnectionString"]
    ?? throw new InvalidOperationException(
        "MongoDB connection string is missing."
    );

var databaseName =
    builder.Configuration["MongoDb:DatabaseName"]
    ?? throw new InvalidOperationException(
        "MongoDB database name is missing."
    );

var collectionName =
    builder.Configuration["MongoDb:CollectionName"]
    ?? throw new InvalidOperationException(
        "MongoDB collection name is missing."
    );

// MongoDB services
builder.Services.AddSingleton<IMongoClient>(
    _ => new MongoClient(mongoConnectionString)
);

builder.Services.AddSingleton<IMongoDatabase>(serviceProvider =>
{
    var client = serviceProvider.GetRequiredService<IMongoClient>();
    return client.GetDatabase(databaseName);
});

builder.Services.AddSingleton<IMongoCollection<Notification>>(
    serviceProvider =>
    {
        var database =
            serviceProvider.GetRequiredService<IMongoDatabase>();

        return database.GetCollection<Notification>(collectionName);
    }
);

// MongoDB health check
builder.Services
    .AddHealthChecks()
    .AddCheck<MongoHealthCheck>("mongodb");

// Eureka Discovery Client
builder.Services.AddEurekaDiscoveryClient();

var app = builder.Build();

// Swagger
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// API endpoints
app.MapControllers();

// Health endpoint used by Eureka
app.MapHealthChecks("/health");

app.MapGet("/", () =>
    Results.Ok(new
    {
        service = "notification-service",
        status = "running"
    })
);

app.Run();
