
using Microsoft.AspNetCore.Mvc;
using MongoDB.Bson;
using MongoDB.Driver;
using NotificationService.DTOs;
using NotificationService.Models;

namespace NotificationService.Controllers;

[ApiController]
[Route("api/notifications")]
public class NotificationsController : ControllerBase
{
    private readonly IMongoCollection<Notification> _notifications;

    public NotificationsController(
        IMongoCollection<Notification> notifications)
    {
        _notifications = notifications;
    }

    // GET: api/notifications
    [HttpGet]
    public async Task<ActionResult<List<Notification>>> GetAll(
        [FromQuery] string? userId)
    {
        var filter = string.IsNullOrWhiteSpace(userId)
            ? Builders<Notification>.Filter.Empty
            : Builders<Notification>.Filter.Eq(x => x.UserId, userId);

        var notifications = await _notifications
            .Find(filter)
            .SortByDescending(x => x.CreatedAt)
            .ToListAsync();

        return Ok(notifications);
    }

    // GET: api/notifications/{id}
    [HttpGet("{id}")]
    public async Task<ActionResult<Notification>> GetById(string id)
    {
        if (!ObjectId.TryParse(id, out _))
            return BadRequest("Invalid notification ID.");

        var notification = await _notifications
            .Find(x => x.Id == id)
            .FirstOrDefaultAsync();

        if (notification == null)
            return NotFound();

        return Ok(notification);
    }

    // POST: api/notifications
    [HttpPost]
    public async Task<ActionResult<Notification>> Create(
        CreateNotificationRequest request)
    {
        var notification = new Notification
        {
            UserId = request.UserId,
            Type = request.Type,
            Title = request.Title,
            Message = request.Message,
            IsRead = false,
            CreatedAt = DateTime.UtcNow
        };

        await _notifications.InsertOneAsync(notification);

        return CreatedAtAction(
            nameof(GetById),
            new { id = notification.Id },
            notification
        );
    }

    // PUT: api/notifications/{id}
    [HttpPut("{id}")]
    public async Task<IActionResult> Update(
        string id,
        UpdateNotificationRequest request)
    {
        if (!ObjectId.TryParse(id, out _))
            return BadRequest("Invalid notification ID.");

        var existing = await _notifications
            .Find(x => x.Id == id)
            .FirstOrDefaultAsync();

        if (existing == null)
            return NotFound();

        existing.UserId = request.UserId;
        existing.Type = request.Type;
        existing.Title = request.Title;
        existing.Message = request.Message;
        existing.IsRead = request.IsRead;

        await _notifications.ReplaceOneAsync(
            x => x.Id == id,
            existing
        );

        return Ok(existing);
    }

    // DELETE: api/notifications/{id}
    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(string id)
    {
        if (!ObjectId.TryParse(id, out _))
            return BadRequest("Invalid notification ID.");

        var result = await _notifications.DeleteOneAsync(
            x => x.Id == id
        );

        if (result.DeletedCount == 0)
            return NotFound();

        return NoContent();
    }
}
