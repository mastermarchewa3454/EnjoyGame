using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyMovement : MonoBehaviour
{
    public float speed = 1;
    public float movementInterval = 1;
    public float stopInterval = 2;

    public Rigidbody2D rb;
    Animator anim;

    Vector2 movement;
    bool moving;

    void Start()
    {
        anim = GameObject.Find("Sprite").GetComponent<Animator>();
        StartCoroutine(NewHeading());
    }

    void FixedUpdate()
    {
        rb.MovePosition(rb.position + movement * speed * Time.fixedDeltaTime);
    }

    IEnumerator NewHeading()
    {
        moving = false;
        while (true)
        {
            moving = !moving;
            anim.SetBool("isMoving", moving);

            if (moving)
            {
                NewHeadingRoutine();
                yield return new WaitForSeconds(movementInterval);
            }
            else
            {
                StopHeading();
                yield return new WaitForSeconds(stopInterval);
            }
        }
    }

    void StopHeading()
    {
        movement.x = 0;
        movement.y = 0;
    }

    void NewHeadingRoutine()
    {
        movement.x = Random.Range(-1f, 1f);
        movement.y = Random.Range(-1f, 1f);
    }
}
