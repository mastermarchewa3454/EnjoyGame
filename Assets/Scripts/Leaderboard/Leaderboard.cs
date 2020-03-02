using System.IO;
using UnityEngine;

public class Leaderboard : MonoBehaviour
{
    [SerializeField] private int maxLeaderboardEntries = 10;
    [SerializeField] private Transform highscoresHolderTransform = null;
    [SerializeField] private GameObject leaderboardEntryObject = null;

    [Header("Test")]
    [SerializeField] LeaderboardEntryData testEntryData = new LeaderboardEntryData();

    private string SavePath => $"{Application.persistentDataPath}/highscores.json";

    private void Start(){
        LeaderboardSaveData savedScores = GetSavedScores();

        UpdateUI(savedScores);

        SaveScores(savedScores);
    }

    [ContextMenu("Add Test Entry")]
    public void AddTestEntry(){
        AddEntry(testEntryData);
    }

    private LeaderboardSaveData GetSavedScores(){
        if(!File.Exists(SavePath)){
            File.Create(SavePath).Dispose();
            return new LeaderboardSaveData();
        }

        using(StreamReader stream = new StreamReader(SavePath)){
            string json = stream.ReadToEnd();

            return JsonUtility.FromJson<LeaderboardSaveData>(json);
        }
    }

    public void SaveScores(LeaderboardSaveData leaderboardSaveData){
        using(StreamWriter stream = new StreamWriter(SavePath)){
            string json = JsonUtility.ToJson(leaderboardSaveData, true);
            stream.Write(json);
        }
    }

    private void UpdateUI(LeaderboardSaveData savedScores){
        foreach(Transform child in highscoresHolderTransform){
            Destroy(child.gameObject);
        }

        foreach(LeaderboardEntryData highscore in savedScores.highscores){
            Instantiate(leaderboardEntryObject, highscoresHolderTransform).GetComponent<LeaderboardEntryUI>().Initialise(highscore);
        }
    }

    public void AddEntry(LeaderboardEntryData leaderboardEntryData){
        LeaderboardSaveData savedScores = GetSavedScores();

        bool scoreAdded = false;

        for(int i=0; i<savedScores.highscores.Count; i++){
            if(leaderboardEntryData.entryScore > savedScores.highscores[i].entryScore){
                savedScores.highscores.Insert(i, leaderboardEntryData);
                scoreAdded = true;
                break;
            }
        }

        if(!scoreAdded && savedScores.highscores.Count < maxLeaderboardEntries){
            savedScores.highscores.Add(leaderboardEntryData);
        }

        if(savedScores.highscores.Count > maxLeaderboardEntries){
            savedScores.highscores.RemoveRange(maxLeaderboardEntries, savedScores.highscores.Count - maxLeaderboardEntries);
        }

        UpdateUI(savedScores);

        SaveScores(savedScores);
    }
}
