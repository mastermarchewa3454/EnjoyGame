using System.Collections;
using System.Collections.Generic;
using UnityEngine;
 /// <summary>
 /// LoadQuest class is a class that loads a quest by gathering data from the database and adding it to the list of avaialable quest.
 /// </summary>
public class LoadQuest : MonoBehaviour
{
    List<Quest> quests = new List<Quest>();
    // Start is called before the first frame update
    public void Start()
    {
        TextAsset questdata = Resources.Load<TextAsset>("questdata");
        string[] data = questdata.text.Split(new char[] { '\n' });
        Debug.Log(data.Length);

        for(int i = 1; i < data.Length - 1; i++)
        {
            string[] row = data[i].Split(new char[] { ',' });
            Quest q = new Quest();
            int.TryParse(row[0], out q.QuestionID);
            int.TryParse(row[1], out q.DifficultyLevel);
            q.Question = row[2];
            q.Answer = row[3];

            quests.Add(q);
        }
    }
}
