using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// Handles dropdown for registration
/// </summary>
public class RegisterDropdown : MonoBehaviour
{
    private bool isClassSelected = false;
    private string classSelected; 

    /// <summary>
    /// Handles the data
    /// </summary>
    /// <param name="val">Value of dropdown</param>
    public void HandleInputData(int val)
    {
        switch(val){
            case 1:
            Debug.Log("Class A");
            isClassSelected = true;
            classSelected = "Class A";
            break;
            case 2:
            Debug.Log("Class B");
            isClassSelected = true;
            classSelected = "Class B";
            break;
            case 3:
            Debug.Log("Class C");
            isClassSelected = true;
            classSelected = "Class C";
            break;
            default:
            Debug.Log("Select Your Class");
            isClassSelected = false;
            classSelected = null;
            break;
            
        }
    }

}
