using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Collections;


public class SoloHighscoreTable : MonoBehaviour
{
    private Transform entryContainer;
    private Transform entryTemplate;
    private Transform stageButton;
    private Transform entryTransform;
    private Result[][] highscoreEntryList;
    private List<Transform> highscoreEntryTransformList;
    private string highscoreTable;
    private string buttonPath;

    private DBResultsManager db;

    public void Start(){
        db = FindObjectOfType<DBResultsManager>();
        Button btn1 = GameObject.Find("stage1Button").GetComponent<Button>();
        Button btn2 = GameObject.Find("stage2Button").GetComponent<Button>();
        Button btn3 = GameObject.Find("stage3Button").GetComponent<Button>();
        Button btn4 = GameObject.Find("stage4Button").GetComponent<Button>();
        Button btn5 = GameObject.Find("stage5Button").GetComponent<Button>();
        btn1.onClick.AddListener(delegate{OnClick(1);});
        btn2.onClick.AddListener(delegate{OnClick(2);});
        btn3.onClick.AddListener(delegate{OnClick(3);});
        btn4.onClick.AddListener(delegate{OnClick(4);});
        btn5.onClick.AddListener(delegate{OnClick(5);});

        //Remove entry template
        transform.Find("SoloStage/stageContainer/stageButtonContainer/stage1Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("SoloStage/stageContainer/stageButtonContainer/stage2Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("SoloStage/stageContainer/stageButtonContainer/stage3Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("SoloStage/stageContainer/stageButtonContainer/stage4Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("SoloStage/stageContainer/stageButtonContainer/stage5Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);

        //Load highscore data
        StartCoroutine(db.GetTop10(callback:data => highscoreEntryList = data));

        //Select Stage 1 by default
        btn1.Select();
        OnClick(1);
    }

    public void OnClick(int index){ 
        if(entryTransform != null)
            Destroy(entryTransform.gameObject);

        if(highscoreEntryTransformList != null){
            foreach(Transform hstransform in highscoreEntryTransformList){
            Destroy(hstransform.gameObject);
            }
        }
        

        highscoreTable = "highscoreTableSS" + index;
        buttonPath = "stage" + index + "Button";

        entryContainer = transform.Find("SoloStage/stageContainer/stageButtonContainer/" + buttonPath + "/highscoreEntryContainer").transform;
        entryTemplate = entryContainer.transform.Find("highscoreEntryTemplate");

        entryTemplate.gameObject.SetActive(false);
        
        DisplayLeaderboard(index);
    }

    private void DisplayLeaderboard(int index){
        Result[] hsStageEntryList = highscoreEntryList[index];
        List<Result> resultList = new List<Result>(hsStageEntryList);

        //Sort highscore data
        resultList.Sort((highscoreEntry1,highscoreEntry2)=>highscoreEntry2.score.CompareTo(highscoreEntry1.score));

        highscoreEntryTransformList = new List<Transform>();
        int count = 0;
        foreach (Result highscoreEntry in resultList){
            if(count>9){
                return;
            }
            CreateHighscoreEntryTransform(highscoreEntry, entryContainer, highscoreEntryTransformList);
            count++;
        }
    }

    private void CreateHighscoreEntryTransform(Result highscoreEntry, Transform container, List<Transform> transformList){
        entryTransform = Instantiate(entryTemplate, container);
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

        string name = highscoreEntry.userId;
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
}
