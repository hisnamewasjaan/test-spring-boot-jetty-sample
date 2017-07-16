module MainButton exposing (..)

import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)



main =
  Html.beginnerProgram
    { model = model
    , view = view
    , update = update
    }



-- MODEL


type alias Model = Int


model : Model
model =
  0



-- UPDATE


type Msg
  = Increment
  |Decrement
  |Double
  |Half


--update : Msg -> Model -> Model
update msg model =

  case msg of
    Increment ->
      model + 11

    Decrement ->
      model - 1

    Double ->
      model * 2

    Half ->
      model // 2



-- VIEW


view : Model -> Html Msg
view model =
  div []
    [ button [ onClick Decrement ] [ text "-" ]
    , button [ onClick Half ] [ text "/2" ]
    , div [] [ text (toString model) ]
    , button [ onClick Increment ] [ text "+" ]
    , button [ onClick Double ] [ text "x2" ]
    ]
