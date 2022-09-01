import csstype.px
import csstype.rgb
import react.FC
import react.Props
import emotion.react.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState

external interface WelcomeProps : Props {
  var name: String
  var isConnected: Boolean
}

val ConnectionStatusView = FC<WelcomeProps> { props ->
  var name by useState(props.name)
  val isConnected by useState(props.isConnected)
  div {
    if(isConnected) {
      css {
        padding = 5.px
        backgroundColor = rgb(8, 97, 22)
        color = rgb(56, 246, 137)
      }
    }else{
      css {
        padding = 5.px
        backgroundColor = rgb(255, 0, 0)
        color = rgb(255, 255, 255)
      }
    }
    +"Hello, $name"
  }
  input {
    css {
      marginTop = 5.px
      marginBottom = 5.px
      fontSize = 14.px
    }
    type = InputType.text
    value = name
    onChange = { event ->
      name = event.target.value
    }
  }
}