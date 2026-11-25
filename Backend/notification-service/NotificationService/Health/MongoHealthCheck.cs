
using Microsoft.Extensions.Diagnostics.HealthChecks;
using MongoDB.Bson;
using MongoDB.Driver;

namespace NotificationService.Health;

public class MongoHealthCheck : IHealthCheck
{
    private readonly IMongoDatabase _database;

    public MongoHealthCheck(IMongoDatabase database)
    {
        _database = database;
    }

    public async Task<HealthCheckResult> CheckHealthAsync(
        HealthCheckContext context,
        CancellationToken cancellationToken = default)
    {
        try
        {
            await _database.RunCommandAsync<BsonDocument>(
                new BsonDocument("ping", 1),
                cancellationToken: cancellationToken
            );

            return HealthCheckResult.Healthy(
                "MongoDB connection is healthy."
            );
        }
        catch (Exception exception)
        {
            return HealthCheckResult.Unhealthy(
                "MongoDB connection failed.",
                exception
            );
        }
    }
}
