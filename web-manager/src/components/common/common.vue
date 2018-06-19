<script>
const table_page_node = 1;
const table_size_node = 10;
const domain = "http://127.0.0.1:8081";
// const domain = "http://api.timezerg.com";
const domain_yearbook = "https://yearbook.timezerg.com";
const doamin_image = "http://xu-1253743633.file.myqcloud.com";

const url_node_list = domain + "/node/list";
const url_node_add = domain + "/node/save";
const url_node_edit_init = domain + "/node/edit/init";
const url_node_edit_save = domain + "/node/edit/submit";
const url_node_edit_init_tag = domain + "/node/edit/init/tag";
const url_node_edit_init_relate = domain + "/node/edit/init/relate";
const url_node_edit_relate_giant_save = domain + "/node/edit/relate/giant/save";
const url_node_edit_relate_giant_delete =
  domain + "/node/edit/relate/giant/delete";
const url_node_edit_relate_nation_save =
  domain + "/node/edit/relate/nation/save";
const url_node_edit_relate_nation_delete =
  domain + "/node/edit/relate/nation/delete";
const url_node_edit_relate_reference_save =
  domain + "/node/edit/relate/reference/save";
const url_node_edit_relate_reference_delete =
  domain + "/node/edit/relate/reference/delete";

const url_node_edit_tag_save = domain + "/node/edit/tag/save";
const url_node_edit_tag_delete = domain + "/node/edit/tag/delete";

const url_civilization_list = domain + "/civilization/list";
const url_civilization_add = domain + "/civilization/save";
const url_civilization_edit_init = domain + "/civilization/edit/init";
const url_civilization_edit_save = domain + "/civilization/edit/save";
const url_civilization_upload = domain + "/civilization/upload";
const url_civilization_first = domain + "/civilization/first";
const url_civilization_down = domain + "/civilization/down";
const url_nation_list = domain + "/nation/list";
const url_nation_add = domain + "/nation/save";
const url_nation_edit_init = domain + "/nation/edit/init";
const url_nation_edit_init_tag = domain + "/nation/edit/init/tag";
const url_nation_edit_init_relate = domain + "/nation/edit/init/relate";
const url_nation_edit_save = domain + "/nation/edit/save";
const url_nation_edit_relate_giant_save =
  domain + "/nation/edit/relate/giant/save";
const url_nation_edit_relate_giant_delete =
  domain + "/nation/edit/relate/giant/delete";
const url_nation_edit_relate_institution_save =
  domain + "/nation/edit/relate/institution/save";
const url_nation_edit_relate_institution_delete =
  domain + "/nation/edit/relate/institution/delete";
const url_nation_edit_relate_reference_save =
  domain + "/nation/edit/relate/reference/save";
const url_nation_edit_relate_reference_delete =
  domain + "/nation/edit/relate/reference/delete";
const url_nation_edit_relate_period_save =
  domain + "/nation/edit/relate/period/save";
const url_nation_edit_relate_period_delete =
  domain + "/nation/edit/relate/period/delete";
const url_nation_tag_save = domain + "/nation/edit/tag/save";
const url_nation_tag_delete = domain + "/nation/edit/tag/delete";
const url_period_list = domain + "/period/list";
const url_period_add = domain + "/period/save";
const url_period_edit_init = domain + "/period/edit/init";
const url_period_edit_init_relate = domain + "/period/edit/init/relate";
const url_period_edit_init_tag = domain + "/period/edit/init/tag";
const url_period_edit_save = domain + "/period/edit/save";
const url_period_tag_save = domain + "/period/edit/tag/save";
const url_period_tag_delete = domain + "/period/edit/tag/delete";
const url_period_relate_civilization_save =
  domain + "/period/edit/relate/civilization/save";
const url_period_relate_civilization_delete =
  domain + "/period/edit/relate/civilization/delete";
const url_period_relate_nation_save =
  domain + "/period/edit/relate/nation/save";
const url_period_relate_nation_delete =
  domain + "/period/edit/relate/nation/delete";
const url_period_relate_reference_save =
  domain + "/period/edit/relate/reference/save";
const url_period_relate_reference_delete =
  domain + "/period/edit/relate/reference/delete";
const url_continent_add = domain + "/continent/save";
const url_index = domain + "/civilization/index";
const url_civilization_index_list = domain + "/civilizationindex/list";
const url_civilization_index_add = domain + "/civilizationindex/add";
const url_civilization_index_del = domain + "/civilizationindex/del";
const url_giant_list = domain + "/giant/list";
const url_giant_add = domain + "/giant/save";
const url_giant_edit_init = domain + "/giant/edit/init";
const url_giant_edit_init_relate = domain + "/giant/edit/init/relate";
const url_giant_edit_init_tag = domain + "/giant/edit/init/tag";
const url_giant_edit_init_relation = domain + "/giant/edit/init/relation";
const url_giant_edit_save = domain + "/giant/edit/save";
const url_giant_edit_relate_nation_save =
  domain + "/giant/edit/relate/nation/save";
const url_giant_edit_relate_nation_delete =
  domain + "/giant/edit/relate/nation/delete";
const url_giant_edit_relate_reference_save =
  domain + "/giant/edit/relate/reference/save";
const url_giant_edit_relate_reference_delete =
  domain + "/giant/edit/relate/reference/delete";
const url_giant_edit_relate_node_save = domain + "/giant/edit/relate/node/save";
const url_giant_edit_relate_node_delete =
  domain + "/giant/edit/relate/node/delete";
const url_giant_edit_tag_save = domain + "/giant/edit/tag/save";
const url_giant_edit_tag_delete = domain + "/giant/edit/tag/delete";
const url_giant_relation_save = domain + "/giant/relation/save";
const url_giant_relation_delete = domain + "/giant/relation/delete";
const url_institution_list = domain + "/institution/list";
const url_institution_add = domain + "/institution/save";
const url_reference_list = domain + "/reference/list";
const url_reference_add = domain + "/reference/save";
const url_reference_edit_init = domain + "/reference/edit/init";
const url_reference_edit_save = domain + "/reference/edit/save";
const url_reference_edit_init_relate = domain + "/reference/edit/init/relate";
const url_reference_relate_giant_save =
  domain + "/reference/edit/relate/giant/save";
const url_reference_relate_giant_delete =
  domain + "/reference/edit/relate/giant/delete";
const url_reference_relate_node_save =
  domain + "/reference/edit/relate/node/save";
const url_reference_relate_node_delete =
  domain + "/reference/edit/relate/node/delete";
const url_reference_relate_period_save =
  domain + "/reference/edit/relate/period/save";
const url_reference_relate_period_delete =
  domain + "/reference/edit/relate/period/delete";
const url_reference_relate_nation_save =
  domain + "/reference/edit/relate/nation/save";
const url_reference_relate_nation_delete =
  domain + "/reference/edit/relate/nation/delete";
const url_reference_edit_init_tag = domain + "/reference/edit/init/tag";
const url_reference_tag_save = domain + "/reference/edit/tag/save";
const url_reference_tag_delete = domain + "/reference/edit/tag/delete";

const url_relation_list = domain + "/relation/list";
const url_relation_edit_init = domain + "/relation/edit/init";
const url_relation_edit_save = domain + "/relation/edit/save";

const url_search_nation = domain + "/nation/search";
const url_search_giant = domain + "/giant/search";
const url_search_civilization = domain + "/civilization/search";
const url_search_continent = domain + "/continent/search";
const url_search_institution = domain + "/institution/search";
const url_search_period = domain + "/period/search";
const url_search_tag = domain + "/tag/search";
const url_search_reference = domain + "/reference/search";
const url_search_node = domain + "/node/search";

//yearbook

export default {
  table_page_node, //
  table_size_node, //
  domain,
  doamin_image,
  url_index,
  url_node_list,
  url_node_add,
  url_node_edit_init,
  url_node_edit_save,
  url_node_edit_init_relate,
  url_node_edit_init_tag,
  url_node_edit_relate_giant_save,
  url_node_edit_relate_giant_delete,
  url_node_edit_relate_nation_save,
  url_node_edit_relate_nation_delete,
  url_node_edit_relate_reference_save,
  url_node_edit_relate_reference_delete,
  url_node_edit_tag_save,
  url_node_edit_tag_delete,
  url_civilization_list,
  url_civilization_add,
  url_civilization_edit_init,
  url_civilization_upload,
  url_civilization_edit_save,
  url_civilization_first,
  url_civilization_down,
  url_nation_list,
  url_nation_add,
  url_nation_edit_init,
  url_nation_edit_init_tag,
  url_nation_edit_init_relate,
  url_nation_edit_save,
  url_nation_edit_relate_giant_save,
  url_nation_edit_relate_giant_delete,
  url_nation_edit_relate_institution_save,
  url_nation_edit_relate_institution_delete,
  url_nation_edit_relate_reference_save,
  url_nation_edit_relate_reference_delete,
  url_nation_edit_relate_period_save,
  url_nation_edit_relate_period_delete,
  url_nation_tag_save,
  url_nation_tag_delete,
  url_period_list,
  url_period_add,
  url_period_edit_init,
  url_period_edit_init_tag,
  url_period_edit_save,
  url_period_tag_save,
  url_period_tag_delete,
  url_period_edit_init_relate,
  url_period_relate_civilization_save,
  url_period_relate_civilization_delete,
  url_period_relate_nation_save,
  url_period_relate_nation_delete,
  url_period_relate_reference_save,
  url_period_relate_reference_delete,
  url_search_nation,
  url_search_period,
  url_search_civilization,
  url_search_continent,
  url_search_giant,
  url_civilization_index_list,
  url_civilization_index_add,
  url_civilization_index_del,
  url_giant_list,
  url_giant_add,
  url_giant_edit_init,
  url_giant_edit_init_relate,
  url_giant_edit_init_tag,
  url_giant_edit_init_relation,
  url_giant_edit_save,
  url_giant_edit_relate_nation_save,
  url_giant_edit_relate_nation_delete,
  url_giant_edit_relate_reference_save,
  url_giant_edit_relate_reference_delete,
  url_giant_edit_relate_node_save,
  url_giant_edit_relate_node_delete,
  url_giant_edit_tag_save,
  url_giant_edit_tag_delete,
  url_giant_relation_save,
  url_giant_relation_delete,
  url_institution_list,
  url_institution_add,
  url_search_institution,
  url_search_tag,
  url_reference_list,
  url_reference_add,
  url_reference_edit_init,
  url_reference_edit_init_relate,
  url_reference_edit_init_tag,
  url_reference_edit_save,
  url_reference_relate_giant_save,
  url_reference_relate_giant_delete,
  url_reference_relate_node_save,
  url_reference_relate_node_delete,
  url_reference_relate_period_save,
  url_reference_relate_period_delete,
  url_reference_relate_nation_save,
  url_reference_relate_nation_delete,
  url_reference_tag_save,
  url_reference_tag_delete,
  url_search_reference,
  url_search_node,
  url_relation_list,
  url_relation_edit_init,
  url_relation_edit_save
};
</script>

