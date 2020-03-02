using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LeaderboardEntryUI : MonoBehaviour
{
    [SerializeField] private Text entryNameText = null;
    [SerializeField] private Text entryScoreText = null;

    public void Initialise(LeaderboardEntryData leaderboardEntryData){
        entryNameText.text = leaderboardEntryData.entryName;
        entryScoreText.text = leaderboardEntryData.entryScore.ToString();
    }
}
