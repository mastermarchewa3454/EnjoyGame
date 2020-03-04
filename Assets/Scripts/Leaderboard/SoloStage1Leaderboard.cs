using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class SoloStage1Leaderboard : MonoBehaviour
{
    private Transform entryContainer;
    private Transform entryTemplate;
    private List<HighscoreEntry> highscoreEntryList;
    private List<Transform> highscoreEntryTransformList;

    public void Awake(){   
        entryContainer = transform.Find("highscoreEntryContainer").transform;
        entryTemplate = entryContainer.transform.Find("highscoreEntryTemplate");

        entryTemplate.gameObject.SetActive(false);

        AddHighscoreEntry("Han Liong", 123456);

        //Load highscore data
        string jsonString = PlayerPrefs.GetString("highscoreTableSS1");
        Highscores highscores = JsonUtility.FromJson<Highscores>(jsonString);
        highscoreEntryList = highscores.highscoreEntryList;

        highscoreEntryList.Sort((highscoreEntry1,highscoreEntry2)=>highscoreEntry2.score.CompareTo(highscoreEntry1.score));

        highscoreEntryTransformList = new List<Transform>();

        foreach (HighscoreEntry highscoreEntry in highscoreEntryList){
            CreateHighscoreEntryTransform(highscoreEntry, entryContainer, highscoreEntryTransformList);
        }

    }

    private void CreateHighscoreEntryTransform(HighscoreEntry highscoreEntry, Transform container, List<Transform> transformList){
        Transform entryTransform = Instantiate(entryTemplate, container);
        float templateHeight = 51f;
        RectTransform entryRectTransform = entryTransform.GetComponent<RectTransform>();
        entryRectTransform.anchoredPosition = new Vector2(0, -templateHeight * transformList.Count);
        entryTransform.gameObject.SetActive(true);

        int rank = transformList.Count + 1;

        string rankString;

        entryTransform.Find("bronzecrown").gameObject.SetActive(false);
        entryTransform.Find("silvercrown").gameObject.SetActive(false);
        entryTransform.Find("goldcrown").gameObject.SetActive(false);

        switch(rank){

            case 1: 
            rankString = "1ST";
            entryTransform.Find("goldcrown").gameObject.SetActive(true);
            break;

            case 2:
            rankString = "2ND";
            entryTransform.Find("silvercrown").gameObject.SetActive(true);
            break;

            case 3:
            rankString = "3RD";
            entryTransform.Find("bronzecrown").gameObject.SetActive(true);
            break;
            
            default:
            rankString = rank.ToString() + "TH";
            break;
        }
            
        entryTransform.Find("rankText").GetComponent<Text>().text = rankString;

        string name = highscoreEntry.name;
        entryTransform.Find("nameText").GetComponent<Text>().text = name;

        int score = highscoreEntry.score;
        entryTransform.Find("scoreText").GetComponent<Text>().text = score.ToString();

        //Highlight background for odd entries
        entryTransform.Find("entryBG").gameObject.SetActive(rank % 2 == 1);

        //Highlight text for first player
        if(rank == 1){
            entryTransform.Find("rankText").GetComponent<Text>().color = Color.yellow;
            entryTransform.Find("nameText").GetComponent<Text>().color = Color.yellow;
            entryTransform.Find("scoreText").GetComponent<Text>().color = Color.yellow;
        }

        transformList.Add(entryTransform);

        
    }

    private void AddHighscoreEntry(string name, int score){
        HighscoreEntry highscoreEntry = new HighscoreEntry{name = name, score = score};
        Highscores highscores;

        //Load highscore data
        string jsonString = PlayerPrefs.GetString("highscoreTableSS1");
        if(jsonString == null){
            highscores = new Highscores();
        }
        else{
            highscores = JsonUtility.FromJson<Highscores>(jsonString);
        }

        highscores.highscoreEntryList.Add(highscoreEntry);

        //Limit number of entries on table to 10
        if(highscores.highscoreEntryList.Count > 10){
            highscores.highscoreEntryList.Sort((highscoreEntry1,highscoreEntry2)=>highscoreEntry2.score.CompareTo(highscoreEntry1.score));
            highscores.highscoreEntryList.RemoveAt(10);
        }

        //Save highscore data
        string json = JsonUtility.ToJson(highscores);
        PlayerPrefs.SetString("highscoreTableSS1", json);
        PlayerPrefs.Save();

    }

    private class Highscores{
        public List<HighscoreEntry> highscoreEntryList;
    }

    [System.Serializable]
    private class HighscoreEntry{
        public string name;
        public int score;
    }
}
