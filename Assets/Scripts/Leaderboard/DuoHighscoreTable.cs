using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Collections;


public class DuoHighscoreTable : MonoBehaviour
{
    private Transform entryContainer;
    private Transform entryTemplate;
    private Transform stageButton;
    private Transform entryTransform;
    private List<Transform> highscoreEntryTransformList;
    private string highscoreTable;
    private string buttonPath;

    private DBResultsManager db;

    private DuoResult[] ra1, ra2, ra3, ra4, ra5;

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
        transform.Find("DuoStage/stageContainer/stageButtonContainer/stage1Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("DuoStage/stageContainer/stageButtonContainer/stage2Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("DuoStage/stageContainer/stageButtonContainer/stage3Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("DuoStage/stageContainer/stageButtonContainer/stage4Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);
        transform.Find("DuoStage/stageContainer/stageButtonContainer/stage5Button/highscoreEntryContainer").transform.Find("highscoreEntryTemplate").gameObject.SetActive(false);

        //Load highscore data
        StartCoroutine(db.GetDuoTop10(1, callback:data => ra1 = data));
        StartCoroutine(db.GetDuoTop10(2, callback:data => ra2 = data));
        StartCoroutine(db.GetDuoTop10(3, callback:data => ra3 = data));
        StartCoroutine(db.GetDuoTop10(4, callback:data => ra4 = data));
        StartCoroutine(db.GetDuoTop10(5, callback:data => {
            ra5 = data;
            btn1.Select();
            //Select Stage 1 by default
            OnClick(1);
        }
        ));

        
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

        entryContainer = transform.Find("DuoStage/stageContainer/stageButtonContainer/" + buttonPath + "/highscoreEntryContainer").transform;
        entryTemplate = entryContainer.transform.Find("highscoreEntryTemplate");

        entryTemplate.gameObject.SetActive(false);
        
        switch(index){
            case 1:
            DisplayLeaderboard(ra1);
            break;
            case 2:
            DisplayLeaderboard(ra2);
            break;
            case 3:
            DisplayLeaderboard(ra3);
            break;
            case 4:
            DisplayLeaderboard(ra4);
            break;
            case 5:
            DisplayLeaderboard(ra5);
            break;
        }
    }

    private void DisplayLeaderboard(DuoResult[] ra){
        List<DuoResult> resultList = new List<DuoResult>(ra);

        //Sort highscore data
        resultList.Sort((highscoreEntry1,highscoreEntry2)=>highscoreEntry2.score.CompareTo(highscoreEntry1.score));

        highscoreEntryTransformList = new List<Transform>();
        int count = 0;
        foreach (DuoResult highscoreEntry in resultList){
            if(count>9){
                return;
            }
            CreateHighscoreEntryTransform(highscoreEntry, entryContainer, highscoreEntryTransformList);
            count++;
        }
    }

    private void CreateHighscoreEntryTransform(DuoResult highscoreEntry, Transform container, List<Transform> transformList){
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

        string name = highscoreEntry.userId1 + " & " + highscoreEntry.userId2;
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
