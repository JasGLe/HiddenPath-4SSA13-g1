
using System.ComponentModel.DataAnnotations;

namespace NotificationService.DTOs;

public class CreateNotificationRequest
{
    [Required]
    public string UserId { get; set; } = string.Empty;

    [Required]
    public string Type { get; set; } = string.Empty;

    [Required]
    public string Title { get; set; } = string.Empty;

    [Required]
    public string Message { get; set; } = string.Empty;
}
