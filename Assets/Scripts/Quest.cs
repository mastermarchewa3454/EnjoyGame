using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Quest class to store the quest as an entity.
/// </summary>
[System.Serializable]

public class Quest
{
    public int QuestionID;
    public int DifficultyLevel;
    public string Question;
    public string Answer;
}